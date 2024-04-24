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
@Table(name = "node")
public class Node {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "x_coordinate")
    private Integer xCoordinate;

    @Column(name = "y_coordinate")
    private Integer yCoordinate;

}