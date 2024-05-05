package info.smartfactory.domain.mission.repository;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("""
            SELECT s
            FROM Submission s JOIN FETCH s.arriveNode n
            WHERE s.mission.id = :missionId
            """)
    List<Submission> getSubmissions(Long missionId);
}