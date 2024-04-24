package info.smartfactory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "mission", schema = "smart-factory")
public class Mission {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "mission_created_at")
    private Instant missionCreatedAt;

    @Column(name = "mission_finished_at")
    private Instant missionFinishedAt;

}