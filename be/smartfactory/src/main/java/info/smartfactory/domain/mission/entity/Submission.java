package info.smartfactory.domain.mission.entity;

import info.smartfactory.domain.common.BaseTimeEntity;
import info.smartfactory.domain.node.entity.Node;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Submission extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "arrive_node_id")
    private Node arriveNode;

    @Column(name = "submission_order", nullable = false)
    private Integer submissionOrder;

}