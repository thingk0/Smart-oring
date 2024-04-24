package info.smartfactory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "submission", schema = "smart-factory")
public class Submission {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "mission_id", nullable = false)
    private Long missionId;

    @Column(name = "arrive_node_id", nullable = false)
    private Long arriveNodeId;

    @Column(name = "submission_order")
    private Integer submissionOrder;

}