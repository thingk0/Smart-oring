package info.smartfactory.domain.node.entity;

import info.smartfactory.domain.node.entity.Node;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "storage")
public class Storage {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "node_id", nullable = false)
    private Node node;

    @Column(name = "entrance_direction")
    private Integer entranceDirection;

}