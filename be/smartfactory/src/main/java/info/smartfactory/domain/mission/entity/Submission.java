package info.smartfactory.domain.mission.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import info.smartfactory.domain.common.BaseTimeEntity;
import info.smartfactory.domain.node.entity.Node;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "submission_history")
public class Submission extends BaseTimeEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id")
    @JsonBackReference
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "arrive_node_id")
    private Node arriveNode;

    @Column(name = "submission_order", nullable = false)
    private Integer submissionOrder;

    public static Submission createSubmission(
            Node arriveNode,
            Integer submissionOrder
    ){
        Submission submission = new Submission();

        submission.submissionOrder = submissionOrder;
        submission.arriveNode = arriveNode;

        return submission;
    }

    public void assignMission(Mission mission) {
        this.mission = mission;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "id=" + id +
                ", arriveNode= [" + arriveNode.getXCoordinate() + " " + arriveNode.getYCoordinate() + "] " +
                ", submissionOrder=" + submissionOrder +
                '}';
    }
}