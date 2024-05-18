package info.smartfactory.domain.bottleneck.repository;

import info.smartfactory.domain.bottleneck.entity.Bottleneck;

import java.time.LocalDateTime;
import java.util.List;

import info.smartfactory.domain.dashboard.service.MissionStatusDto;
import info.smartfactory.domain.mission.entity.constant.MissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface BottleneckRepository extends JpaRepository<Bottleneck, Long> {

    @NonNull
    @Query("SELECT b FROM Bottleneck b LEFT JOIN FETCH b.mission")
    List<Bottleneck> findAllWithMission();

    @Query("""
        SELECT b
        FROM Bottleneck b
        WHERE b.bottleneckCreatedAt >= :startDate AND b.bottleneckCreatedAt <= :endDate
        """)
    List<Bottleneck> findAllByBottleneckCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("""
        SELECT b
        FROM Bottleneck b
        WHERE b.bottleneckCreatedAt >= :startDate
        """)
    List<Bottleneck> findAllByBottleneckFromStartDate(@Param("startDate") LocalDateTime startDate);

    @Query("""
        SELECT b
        FROM Bottleneck b
        WHERE b.bottleneckCreatedAt <= :endDate
        """)
    List<Bottleneck> findAllByBottleneckBeforeEndDate(@Param("endDate") LocalDateTime endDate);

    @Query("""
        SELECT b
        FROM Bottleneck b
        WHERE (select m.missionType from Mission m where b.mission.id = m.id) = :missionType
        """)
    List<Bottleneck> findBottltneckByMissionType(@Param("missionType") MissionType missionType);

    @Query("""
        SELECT b
        FROM Bottleneck b
        WHERE b.bottleneckCreatedAt >= :startDate AND b.bottleneckCreatedAt <= :endDate
        AND (select m.missionType from Mission m where b.mission.id = m.id) = :missionType
        """)
    List<Bottleneck> findByMissionTypeAndDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("missionType") MissionType missionType);

    @Query("""
        SELECT b
        FROM Bottleneck b
        WHERE b.bottleneckCreatedAt >= :startDate
        AND (select m.missionType from Mission m where b.mission.id = m.id) = :missionType
        """)
    List<Bottleneck> findByMissionTypeAndAfterStartDate(@Param("startDate") LocalDateTime startDate, @Param("missionType") MissionType missionType);

    @Query("""
        SELECT b
        FROM Bottleneck b
        WHERE b.bottleneckCreatedAt <= :endDate
        AND (select m.missionType from Mission m where b.mission.id = m.id) = :missionType
        """)
    List<Bottleneck> findByMissionTypeAndBeforeEndDate( @Param("endDate") LocalDateTime endDate, @Param("missionType") MissionType missionType);

}