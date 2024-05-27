package info.smartfactory.amrstreams.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.smartfactory.amrstreams.log.AmrHistoryLog;
import info.smartfactory.amrstreams.serde.AmrHistoryListSerde;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class SerdeConfig {

    /**
     * AmrHistoryLog 클래스를 위한 JsonSerde 빈을 생성합니다.
     *
     * @param objectMapper ObjectMapper 인스턴스
     * @return AmrHistoryLog 타입을 위한 JsonSerde 인스턴스
     */
    @Bean
    public JsonSerde<AmrHistoryLog> amrHistorySerde(ObjectMapper objectMapper) {
        return new JsonSerde<>(AmrHistoryLog.class, objectMapper);
    }

    /**
     * AmrHistoryList 타입을 위한 사용자 정의 Serde 빈을 생성합니다.
     *
     * @param objectMapper ObjectMapper 인스턴스
     * @return AmrHistoryList 타입을 위한 사용자 정의 Serde 인스턴스
     */
    @Bean
    public AmrHistoryListSerde amrHistoryListSerde(ObjectMapper objectMapper) {
        return new AmrHistoryListSerde(objectMapper);
    }

}
