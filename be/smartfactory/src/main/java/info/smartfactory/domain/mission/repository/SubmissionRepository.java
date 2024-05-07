package info.smartfactory.domain.mission.repository;

import info.smartfactory.domain.mission.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    @Query("""
            SELECT m
            FROM Mission m
            JOIN FETCH m.submissionList s
            JOIN FETCH s.arriveNode n
            WHERE s.mission.id = :missionId
            """)
    List<Submission> findByMissionIdWithNodes(@Param("missionId") Long missionId);
}