package info.smartfactory.domain.mission.repository.custom;

import info.smartfactory.domain.mission.service.dto.MissionHistoryDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Page<MissionHistoryDto> fetchMissionHistories(Pageable pageable, List<String> amrTypes, LocalDateTime startTime, LocalDateTime endTime,
                                                         Integer bottleneckSeconds) {

        List<Object> params = new ArrayList<>();
        StringBuilder whereClause = new StringBuilder("WHERE mh.mission_finished_at IS NOT NULL ");

        buildWhereClause(amrTypes, startTime, endTime, bottleneckSeconds, params, whereClause);

        String sql = """
            SELECT mh.id,
                   amr.id AS amrId,
                   amr.amr_code AS amrCode,
                   TIMESTAMPDIFF(SECOND, DATE_ADD(mh.mission_started_at, INTERVAL mh.mission_estimated_time SECOND), mh.mission_finished_at) AS delayTime,
                   mh.mission_started_at,
                   mh.mission_finished_at
            FROM mission_history mh
                     LEFT JOIN amr_history ah ON mh.id = ah.mission_id
                     LEFT JOIN amr ON ah.amr_id = amr.id
            """ + whereClause + """
            ORDER BY mh.mission_finished_at DESC
            LIMIT ? OFFSET ?""";

        params.add(pageable.getPageSize());
        params.add(pageable.getOffset());

        List<MissionHistoryDto> content = jdbcTemplate.query(sql, params.toArray(), missionHistoryRowMapper(pageable));

        String countQuery = """
            SELECT COUNT(*)
            FROM mission_history mh
                     LEFT JOIN amr_history ah ON mh.id = ah.mission_id
                     LEFT JOIN amr ON ah.amr_id = amr.id
            """ + whereClause;

        Integer total = Optional.ofNullable(jdbcTemplate.queryForObject(countQuery, Integer.class, params.toArray())).orElse(0);
        return new PageImpl<>(content, pageable, total);
    }

    private void buildWhereClause(List<String> amrTypes, LocalDateTime startTime, LocalDateTime endTime, Integer bottleneckSeconds,
                                  List<Object> params, StringBuilder whereClause) {
        if (!CollectionUtils.isEmpty(amrTypes)) {
            whereClause.append(" AND amr.amr_code IN (");
            String inClause = String.join(",", amrTypes.stream().map(type -> "?").toArray(String[]::new));
            whereClause.append(inClause).append(") ");
            params.addAll(amrTypes);
        }

        if (startTime != null) {
            whereClause.append(" AND mh.mission_started_at >= ? ");
            params.add(startTime);
        }

        if (endTime != null) {
            whereClause.append(" AND mh.mission_finished_at <= ? ");
            params.add(endTime);
        }

        if (bottleneckSeconds != null) {
            whereClause.append(
                " AND TIMESTAMPDIFF(SECOND, DATE_ADD(mh.mission_started_at, INTERVAL mh.mission_estimated_time SECOND), mh.mission_finished_at) <= ? ");
            params.add(bottleneckSeconds);
        }
    }

    private RowMapper<MissionHistoryDto> missionHistoryRowMapper(Pageable pageable) {
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
