package info.smartfactory.domain.mission.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import jakarta.persistence.Table;
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
@Table(name = "submission")
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





}