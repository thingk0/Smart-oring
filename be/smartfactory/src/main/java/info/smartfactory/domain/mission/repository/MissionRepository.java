package info.smartfactory.domain.mission.repository;

import info.smartfactory.domain.dashboard.service.MissionStatusDto;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.Submission;
import info.smartfactory.domain.mission.repository.custom.MissionRepositoryCustom;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long>, MissionRepositoryCustom {

    @Query("""
            SELECT s
            FROM Submission s JOIN FETCH s.arriveNode n
            WHERE s.mission.id = :missionId
            """)
    List<Submission> getSubmissions(Long missionId);

    @Query("""
            SELECT m
            FROM Mission m
            JOIN FETCH m.submissionList s
            JOIN FETCH s.arriveNode n
            WHERE m.id = :missionId
            """)
    Mission findByMissionIdWithNodes(@Param("missionId") Long missionId);

    @Query("""
        SELECT new info.smartfactory.domain.dashboard.service.MissionStatusDto(m, 
            CASE 
                WHEN (SELECT COUNT(ah) FROM AmrHistory ah WHERE ah.mission = m AND ah.amrStatus = info.smartfactory.domain.history.entity.constant.AmrStatus.ERROR) > 0 THEN true
                ELSE false
            END
        )
        FROM Mission m
        WHERE m.missionFinishedAt IS NOT NULL
        AND m.missionStartedAt >= :yesterdayStart AND m.missionStartedAt <= :now
        """)
    List<MissionStatusDto> getCompleteMissions(@Param("yesterdayStart") LocalDateTime yesterdayStart, @Param("now") LocalDateTime now);

}