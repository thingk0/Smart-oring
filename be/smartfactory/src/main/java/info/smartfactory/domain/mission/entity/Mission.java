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

    @Column(name = "full_path")
    private String fullPath; // JSON 형식의 데이터를 저장할 문자열 필드

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


    public void modifyMission(LocalDateTime missionStartedAt, LocalDateTime missionFinishedAt, LocalDateTime missionEstimatedTime, String fullPath) {
        this.missionStartedAt = missionStartedAt;
        this.missionFinishedAt = missionFinishedAt;
        this.missionEstimatedTime = missionEstimatedTime;
        this.fullPath = fullPath;
    }
}