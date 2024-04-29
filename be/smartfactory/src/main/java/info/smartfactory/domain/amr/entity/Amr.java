package info.smartfactory.domain.amr.entity;

import info.smartfactory.domain.amr.constant.AmrStatus;
import info.smartfactory.domain.common.BaseTimeEntity;
import info.smartfactory.domain.mission.entity.Mission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@NoArgsConstructor
@Entity
@Table(name = "amr", indexes = {
    @Index(name = "idx_amr_amr_code_unq", columnList = "amr_code", unique = true)
})
public class Amr extends BaseTimeEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Column(name = "amr_code", length = 7, nullable = false)
    private String amrCode;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "amr_status", nullable = false)
    private AmrStatus amrStatus = AmrStatus.IDLE;

}