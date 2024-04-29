package info.smartfactory.domain.mission.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
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

    @Column(name = "mission_created_at", updatable = false)
    private LocalDateTime missionCreatedAt;

    @Column(name = "mission_finished_at", updatable = false)
    private LocalDateTime missionFinishedAt;

    @OneToMany
    private List<Submission> submissionList;
}