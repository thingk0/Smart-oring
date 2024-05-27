package info.smartfactory.domain.mission.repository.custom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;

import info.smartfactory.domain.mission.service.dto.MissionHistoryDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    /**
     * 주어진 조건에 따라 미션 기록을 조회합니다.
     *
     * @param pageable          페이징 정보
     * @param amrTypes          조회할 AMR 코드 리스트
     * @param startTime         미션 시작 시간 범위의 시작 시간
     * @param endTime           미션 종료 시간 범위의 종료 시간
     * @param bottleneckSeconds 병목 시간(초) 기준
     * @return 페이지 처리된 미션 기록 목록
     */
    @Override
    public Page<MissionHistoryDto> fetchMissionHistories(Pageable pageable, List<String> amrTypes, LocalDateTime startTime, LocalDateTime endTime,
                                                         Integer bottleneckSeconds) {

        List<Object> params = new ArrayList<>();
        StringBuilder whereClause = new StringBuilder("WHERE mh.mission_finished_at IS NOT NULL ");

        buildWhereClause(amrTypes, startTime, endTime, bottleneckSeconds, params, whereClause);

        String sql = """
            SELECT distinct mh.id,
                   amr.id AS amrId,
                   amr.amr_code AS amrCode,
                   TIMESTAMPDIFF(SECOND, DATE_ADD(mh.mission_started_at, INTERVAL mh.mission_estimated_time SECOND), mh.mission_finished_at) AS delayTime,
                   mh.mission_started_at,
                   mh.mission_finished_at
            FROM mission_history mh
                     LEFT JOIN amr_history ah ON mh.id = ah.mission_id
                     LEFT JOIN amr ON mh.amr_id = amr.id
            """ + whereClause + """
            ORDER BY mh.mission_finished_at DESC
            LIMIT ? OFFSET ?""";

        params.add(pageable.getPageSize());
        params.add(pageable.getOffset());

        List<MissionHistoryDto> content = jdbcTemplate.query(sql, params.toArray(), missionHistoryRowMapper());

        String countQuery = """
            SELECT COUNT(*)
            FROM mission_history mh
                     LEFT JOIN amr_history ah ON mh.id = ah.mission_id
                     LEFT JOIN amr ON ah.amr_id = amr.id
            """ + whereClause;

        Integer total = Optional.ofNullable(jdbcTemplate.queryForObject(countQuery, Integer.class, params.toArray())).orElse(0);
        return new PageImpl<>(content, pageable, total);
    }

    /**
     * WHERE 절을 빌드하여 주어진 조건에 맞는 SQL 쿼리를 만듭니다.
     *
     * @param amrTypes          조회할 AMR 코드 리스트
     * @param startTime         미션 시작 시간 범위의 시작 시간
     * @param endTime           미션 종료 시간 범위의 종료 시간
     * @param bottleneckSeconds 병목 시간(초) 기준
     * @param params            쿼리 파라미터 리스트
     * @param whereClause       WHERE 절 StringBuilder
     */
    private void buildWhereClause(List<String> amrTypes, LocalDateTime startTime, LocalDateTime endTime, Integer bottleneckSeconds,
                                  List<Object> params, StringBuilder whereClause) {

        // AMR 코드 리스트가 비어있지 않은 경우
        if (!CollectionUtils.isEmpty(amrTypes)) {
            whereClause.append(" AND amr.amr_code IN (");
            whereClause.append(String.join(",", amrTypes.stream().map(type -> "?").toArray(String[]::new)));
            whereClause.append(") ");
            params.addAll(amrTypes);
        }

        // 시작 시간이 주어진 경우
        if (startTime != null) {
            whereClause.append(" AND mh.mission_started_at >= ? ");
            params.add(startTime);
        }

        // 종료 시간이 주어진 경우
        if (endTime != null) {
            whereClause.append(" AND mh.mission_finished_at <= ? ");
            params.add(endTime);
        }

        // 병목 시간이 주어진 경우
        if (bottleneckSeconds != null) {
            whereClause.append(
                " AND TIMESTAMPDIFF(SECOND, DATE_ADD(mh.mission_started_at, INTERVAL mh.mission_estimated_time SECOND), mh.mission_finished_at) <= ? ");
            params.add(bottleneckSeconds);
        }
    }


    /**
     * 미션 기록 DTO를 매핑하는 RowMapper 를 생성합니다.
     *
     * @return 미션 기록 DTO를 매핑하는 RowMapper
     */
    private RowMapper<MissionHistoryDto> missionHistoryRowMapper() {
        return (rs, rowNum) -> new MissionHistoryDto(
            rs.getLong("id"),
            rs.getLong("amrId"),
            rs.getString("amrCode"),
            rs.getInt("delayTime"),
            rs.getTimestamp("mission_started_at").toLocalDateTime(),
            rs.getTimestamp("mission_finished_at").toLocalDateTime()
        );
    }
}
