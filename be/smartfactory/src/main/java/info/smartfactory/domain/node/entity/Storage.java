package info.smartfactory.domain.node.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "storage", schema = "smart-factory")
public class Storage {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "node_id", nullable = false)
    private Long nodeId;

    @Column(name = "entrance_direction")
    private Integer entranceDirection;

}