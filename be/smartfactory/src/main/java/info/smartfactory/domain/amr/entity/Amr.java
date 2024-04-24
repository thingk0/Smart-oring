package info.smartfactory.domain.amr.entity;

import info.smartfactory.domain.mission.entity.Mission;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "amr")
public class Amr {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Column(name = "amr_code", length = 36)
    private String amrCode;

}