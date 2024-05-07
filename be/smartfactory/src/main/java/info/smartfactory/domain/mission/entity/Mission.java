package info.smartfactory.domain.mission.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import info.smartfactory.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "mission")
public class Mission extends BaseTimeEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mission_started_at", updatable = false)
    private LocalDateTime missionStartedAt;

    @Column(name = "mission_finished_at", updatable = false)
    private LocalDateTime missionFinishedAt;

    @Column(name = "mission_estimated time", updatable = false)
    private LocalDateTime missionEstimatedTime;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Submission> submissionList = new ArrayList<>();

    public static Mission createMission() {
        return new Mission();
    }

    public void addSubmission(Submission submission) {
        submissionList.add(submission);
        submission.assignMission(this);
    }
}