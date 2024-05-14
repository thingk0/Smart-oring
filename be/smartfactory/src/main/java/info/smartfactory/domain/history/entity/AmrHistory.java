package info.smartfactory.domain.history.entity;

import java.time.LocalDateTime;

import info.smartfactory.domain.amr.entity.Amr;
import info.smartfactory.domain.common.BaseTimeEntity;
import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import info.smartfactory.domain.mission.entity.Mission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "amr_history")
public class AmrHistory extends BaseTimeEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "amr_id")
    private Amr amr;

    @Column(name = "battery", nullable = false)
    private Integer battery;

    @Column(name = "x_coordinate", nullable = false)
    private Integer xCoordinate;

    @Column(name = "y_coordinate", nullable = false)
    private Integer yCoordinate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "amr_status", nullable = false)
    private AmrStatus amrStatus = AmrStatus.CHARGING;

    @Column(name = "amr_history_created_at", nullable = false)
    private LocalDateTime amrHistoryCreatedAt;

    public static AmrHistory of(AmrHistoryLog historyLog, Mission mission, Amr amr) {
        return AmrHistory.builder()
                         .amr(amr)
                         .mission(mission)
                         .battery(historyLog.battery())
                         .xCoordinate(historyLog.xCoordinate())
                         .yCoordinate(historyLog.yCoordinate())
                         .amrHistoryCreatedAt(historyLog.amrHistoryCreatedAt())
                         .build();
    }

}