package info.smartfactory.domain.mission.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import info.smartfactory.domain.amr.entity.Amr;
import info.smartfactory.domain.common.BaseTimeEntity;
import info.smartfactory.domain.mission.entity.constant.MissionType;
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
@Table(name = "mission_history")
public class Mission extends BaseTimeEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amr_id")
    private Amr amr;

    @Column(name = "mission_started_at")
    private LocalDateTime missionStartedAt;

    @Column(name = "mission_finished_at")
    private LocalDateTime missionFinishedAt;

    @Column(name = "mission_estimated_time")
    private Integer missionEstimatedTime;

    @Lob
    @Column(name = "full_path", columnDefinition = "TEXT")
    private String fullPath; // JSON 형식의 데이터를 저장할 문자열 필드

    @Column(name = "mission_type")
    @Enumerated(value = EnumType.STRING)
    private MissionType missionType;

    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "mission", cascade = CascadeType.REMOVE)
    private List<Submission> submissionList = new ArrayList<>();

    public static Mission createMission() {
        return new Mission();
    }

    public void addSubmission(Submission submission) {
        submissionList.add(submission);
        submission.assignMission(this);
    }

    public void completeMission(
            LocalDateTime missionStartedAt,
            LocalDateTime missionFinishedAt,
            Integer missionEstimatedTime,
            String fullPath,
            Amr amr) {
        this.missionStartedAt = missionStartedAt;
        this.missionFinishedAt = missionFinishedAt;
        this.missionEstimatedTime = missionEstimatedTime;
        this.fullPath = fullPath;
        this.amr = amr;
    }

    public void modifyMissionType(MissionType missionType) {
        this.missionType = missionType;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", amr=" + amr +
                ", missionStartedAt=" + missionStartedAt +
                ", missionFinishedAt=" + missionFinishedAt +
                ", missionEstimatedTime=" + missionEstimatedTime +
                ", fullPath='" + fullPath + '\'' +
                ", missionType=" + missionType +
                ", submissionList=" + submissionList +
                '}';
    }
}