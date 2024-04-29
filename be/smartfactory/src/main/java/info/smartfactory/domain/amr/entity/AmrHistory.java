package info.smartfactory.domain.amr.entity;

import info.smartfactory.domain.mission.entity.Mission;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "amr_history")
public class AmrHistory {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Column(name = "x_coordinate", nullable = false)
    private Integer xCoordinate;

    @Column(name = "y_coordinate", nullable = false)
    private Integer yCoordinate;

    @Column(name = "amr_history_created_at", nullable = false)
    private Instant amrHistoryCreatedAt;

    @Column(name = "amr_status", nullable = false, length = 50)
    private String amrStatus;

    @Column(name = "battery", nullable = false)
    private Integer battery;

}