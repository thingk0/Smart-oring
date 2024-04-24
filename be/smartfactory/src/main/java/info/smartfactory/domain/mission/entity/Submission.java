package info.smartfactory.domain.mission.entity;

import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.node.entity.Node;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "submission")
public class Submission {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "arrive_node_id", nullable = false)
    private Node arriveNode;

    @Column(name = "submission_order")
    private Integer submissionOrder;

}