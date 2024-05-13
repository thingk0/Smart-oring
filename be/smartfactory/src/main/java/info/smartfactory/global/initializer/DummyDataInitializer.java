package info.smartfactory.global.initializer;

import info.smartfactory.domain.amr.entity.Amr;
import info.smartfactory.domain.amr.repository.AmrRepository;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.constant.MissionType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DummyDataInitializer implements CommandLineRunner {

    private final AmrRepository amrRepository;
    private final JdbcTemplate template;

    @Override
    public void run(String... args) {
        try {
            amrInit();
            missionInit();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void amrInit() {
        List<Amr> amrList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            String amrCode = String.format("AMR%04d", i);
            amrList.add(Amr.builder()
                           .amrCode(amrCode).build());
        }

        batchInsertAmrIfNotExists(amrList);
    }

    private void batchInsertMission(List<Mission> missionList) {
        template.batchUpdate("INSERT INTO mission_history (amr_id, mission_type, mission_started_at, mission_finished_at, mission_estimated_time) "
                                 + "VALUES (?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {

            @Override
            public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
                Mission mission = missionList.get(i);
                ps.setLong(1, mission.getAmr().getId());
                ps.setString(2, mission.getMissionType().toString());
                ps.setTimestamp(3, Timestamp.valueOf(mission.getMissionStartedAt()));
                ps.setTimestamp(4, Timestamp.valueOf(mission.getMissionFinishedAt()));
                ps.setInt(5, mission.getMissionEstimatedTime());
            }

            @Override
            public int getBatchSize() {
                return missionList.size();
            }
        });
    }

    private void missionInit() {

        List<Mission> missionList = new ArrayList<>();
        List<Amr> amrList = amrRepository.findAll();

        for (int i = 1; i <= 1000; i++) {
            LocalDateTime now = LocalDateTime.now();
            missionList.add(Mission.builder()
                                   .amr(amrList.get(ThreadLocalRandom.current().nextInt(amrList.size())))
                                   .missionType(MissionType.values()[ThreadLocalRandom.current().nextInt(MissionType.values().length)])
                                   .missionStartedAt(now)
                                   .missionFinishedAt(now.plusMinutes(ThreadLocalRandom.current().nextInt(60, 600)))
                                   .missionEstimatedTime(ThreadLocalRandom.current().nextInt(1, 101))
                                   .build());
        }

        batchInsertMission(missionList);
    }

    private void batchInsertAmrIfNotExists(List<Amr> amrList) {

        Set<String> existingAmrCodes = new HashSet<>(template.query("SELECT amr_code FROM amr",
                                                                    (rs, rowNum) -> rs.getString("amr_code")));

        List<Amr> newAmrList = new ArrayList<>();
        for (Amr amr : amrList) {
            if (!existingAmrCodes.contains(amr.getAmrCode())) {
                newAmrList.add(amr);
            }
        }

        template.batchUpdate("INSERT INTO amr (amr_code, created_at, updated_at) VALUES (?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
                Amr amr = newAmrList.get(i);
                LocalDateTime now = LocalDateTime.now();
                ps.setString(1, amr.getAmrCode());
                ps.setTimestamp(2, Timestamp.valueOf(now));
                ps.setTimestamp(3, Timestamp.valueOf(now));
            }

            @Override
            public int getBatchSize() {
                return newAmrList.size();
            }
        });
    }


}
