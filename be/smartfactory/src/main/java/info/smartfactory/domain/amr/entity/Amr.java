package info.smartfactory.domain.amr.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "amr", schema = "smart-factory")
public class Amr {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "mission_id", nullable = false)
    private Long missionId;

    @Column(name = "amr_code", length = 36)
    private String amrCode;

}