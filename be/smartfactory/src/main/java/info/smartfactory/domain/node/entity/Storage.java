package info.smartfactory.domain.node.entity;


import info.smartfactory.domain.common.BaseTimeEntity;
import info.smartfactory.domain.node.entity.Node;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "storage")
public class Storage extends BaseTimeEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "node_id", nullable = false)
    private Node node;

    @Column(name = "entrance_direction")
    private Integer entranceDirection;

}