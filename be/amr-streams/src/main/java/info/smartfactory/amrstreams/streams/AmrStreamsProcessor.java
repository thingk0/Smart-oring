package info.smartfactory.amrstreams.streams;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.smartfactory.amrstreams.log.AmrHistoryLog;
import info.smartfactory.amrstreams.log.AmrRealtimeStatusLog;
import info.smartfactory.amrstreams.mapper.AmrHistoryMapper;
import info.smartfactory.amrstreams.serde.AmrHistoryListSerde;
import jakarta.annotation.PostConstruct;

/**
 * AMR 기기의 실시간 상태 로그를 처리하여 데이터베이스에 배치 형태로 저장하는 카프카 스트림즈 애플리케이션입니다.
 */
@Component
public class AmrStreamsProcessor {

    private static final Logger log = LoggerFactory.getLogger(AmrStreamsProcessor.class);

    private final JdbcTemplate template;
    private final AmrHistoryListSerde amrHistoryListSerde;
    private final JsonSerde<AmrHistoryLog> amrHistorySerde;
    private final StreamsBuilder builder;
    private final ObjectMapper objectMapper;

    public AmrStreamsProcessor(JdbcTemplate template, AmrHistoryListSerde amrHistoryListSerde, JsonSerde<AmrHistoryLog> amrHistorySerde,
                               StreamsBuilder builder, ObjectMapper objectMapper) {
        this.template = template;
        this.amrHistoryListSerde = amrHistoryListSerde;
        this.amrHistorySerde = amrHistorySerde;
        this.builder = builder;
        this.objectMapper = objectMapper;
    }

    /**
     * 애플리케이션 초기화 시 스트림즈 처리 파이프라인을 구성합니다.
     */
    @PostConstruct
    public void start() {

        // "amr-history-log" 토픽에서 스트림을 생성합니다.
        KStream<String, String> stream = builder.stream("amr-history-log");

        // 스트림의 각 레코드의 값(value)을 AmrHistoryLog 객체로 변환합니다.
        // 변환에 실패한 경우(null) 필터링합니다.
        stream.mapValues(this::convertToAmrHistoryDto)
              .filter((key, value) -> value != null)

              // 레코드를 mission_id 를 기준으로 그룹화합니다.
              // 키(key)는 Long 타입, 값(value)은 AmrHistoryLog 타입으로 설정합니다.
            .groupBy((key, value) -> value.amr_history_created_at().toString(),
                Grouped.with(Serdes.String(), amrHistorySerde))

              // 1분 단위의 타임 윈도우를 적용합니다.
              .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(15)))

              // 윈도우 내의 레코드를 ArrayList 로 집계합니다.
              // 키(key)는 Long 타입, 값(value)은 AmrHistoryLog 객체 리스트입니다.
              .aggregate(
                  ArrayList::new,
                  (key, value, aggregate) -> {
                      aggregate.add(value);
                      return aggregate;
                  },
                  Materialized.with(Serdes.String(), amrHistoryListSerde))

              // 집계된 결과를 다시 스트림으로 변환합니다.
              // 키(key)는 원래 mission_id 값, 값(value)은 AmrHistoryLog 객체 리스트입니다.
              .toStream()
              .map((windowedKey, value) -> KeyValue.pair(windowedKey.key(), value))

              // 각 윈도우 내의 레코드 리스트를 batchInsert 메서드로 전달하여 데이터베이스에 삽입합니다.
              .foreach((key, batchList) -> batchInsert(batchList));
    }

    /**
     * JSON 형식의 문자열을 AmrHistoryLog 객체로 변환합니다.
     *
     * @param json JSON 형식의 문자열
     * @return AmrHistoryLog 객체, 변환 실패 시 null
     */
    private AmrHistoryLog convertToAmrHistoryDto(String json) {
        try {
            return AmrHistoryMapper.INSTANCE.toHistoryLog(
                objectMapper.readValue(json, AmrRealtimeStatusLog.class));
        } catch (JsonProcessingException e) {
            log.error("JSON 변환 오류", e);
            return null;
        }
    }

    /**
     * amr_history 테이블에 데이터를 삽입하는 SQL 쿼리입니다.
     */
    private static final String INSERT_SQL = """
        INSERT INTO amr_history
        (mission_id, amr_id, battery, x_coordinate, y_coordinate, amr_history_created_at, amr_status, route_visited_for_mission, route_remaining_for_mission, current_stop_duration, has_stuff)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    /**
     * AmrHistoryLog 객체 리스트를 데이터베이스에 배치 삽입합니다.
     *
     * @param records AmrHistoryLog 객체 리스트
     */
    private void batchInsert(List<AmrHistoryLog> records) {
        template.batchUpdate(INSERT_SQL, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(@NonNull java.sql.PreparedStatement preparedStatement, int i) throws SQLException {
                AmrHistoryLog record = records.get(i);
                if (record.mission_id() == null) {
                    preparedStatement.setNull(1, java.sql.Types.BIGINT);
                } else {
                    preparedStatement.setLong(1, record.mission_id());
                }
                Instant instant = record.amr_history_created_at().toInstant(OffsetDateTime.now().getOffset());
                preparedStatement.setLong(2, record.amr_id());
                preparedStatement.setInt(3, record.battery());
                preparedStatement.setInt(4, record.x_coordinate());
                preparedStatement.setInt(5, record.y_coordinate());
                preparedStatement.setTimestamp(6, Timestamp.from(instant));
                preparedStatement.setString(7, record.amr_status().name());
                preparedStatement.setString(8, record.routeVisitedForMission());
                preparedStatement.setString(9, record.routeRemainingForMission());
                preparedStatement.setInt(10, record.currentStopDuration());
                preparedStatement.setBoolean(11, record.hasStuff());
            }

            @Override
            public int getBatchSize() {
                return records.size();
            }
        });

        log.info("Batch insert completed for {} records.", records.size());
    }
}
