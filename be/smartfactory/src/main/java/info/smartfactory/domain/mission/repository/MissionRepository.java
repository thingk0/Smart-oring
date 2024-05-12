package info.smartfactory.domain.mission.repository;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

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
        SELECT m
        FROM Mission m
        WHERE m.missionStartedAt BETWEEN :yesterday AND :today
        """)
    List<Mission> getCompleteMissions(@Param("yesterday") LocalDate yesterday, @Param("today") LocalDate today);
}