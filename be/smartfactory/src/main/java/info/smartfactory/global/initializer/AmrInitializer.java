package info.smartfactory.global.initializer;

import info.smartfactory.domain.amr.entity.Amr;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AmrInitializer implements CommandLineRunner {

    private final JdbcTemplate template;

    @Override
    public void run(String... args) {
        init();
    }

    private void init() {
        List<Amr> amrList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            String amrCode = String.format("AMR%04d", i);
            amrList.add(Amr.builder()
                           .amrCode(amrCode).build());
        }

        batchInsertAmrIfNotExists(amrList);
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
