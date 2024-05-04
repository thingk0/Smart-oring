package info.smartfactory.domain.mission.repository;

import info.smartfactory.domain.mission.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    @Query("""
            SELECT s
            FROM Submission s JOIN FETCH s.arriveNode n
            WHERE s.arriveNode.id = :missionId
            """)
    List<Submission> findByMissionIdWithNodes(Long missionId);
}