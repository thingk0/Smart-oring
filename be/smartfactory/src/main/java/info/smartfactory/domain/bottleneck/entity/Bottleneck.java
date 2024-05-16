package info.smartfactory.domain.bottleneck.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.smartfactory.domain.common.BaseTimeEntity;
import info.smartfactory.domain.mission.entity.Mission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
@Table(name = "bottleneck_history")
public class Bottleneck extends BaseTimeEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x_coordinate", nullable = false)
    private Integer xCoordinate;

    @Column(name = "y_coordinate", nullable = false)
    private Integer yCoordinate;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Column(name = "bottleneck_period", nullable = false)
    private Integer bottleneckPeriod;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "bottleneck_created_at", nullable = false)
    private LocalDateTime bottleneckCreatedAt;
}