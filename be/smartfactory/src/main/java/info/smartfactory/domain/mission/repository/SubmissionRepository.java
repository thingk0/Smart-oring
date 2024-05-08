package info.smartfactory.domain.mission.repository;

import info.smartfactory.domain.mission.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {


}