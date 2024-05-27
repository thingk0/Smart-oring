package info.smartfactory.domain.history.repository;

import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.history.repository.custom.AmrHistoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AmrHistoryRepository extends JpaRepository<AmrHistory, Long>, AmrHistoryRepositoryCustom {
    @Query("""
           SELECT a
           FROM AmrHistory a
           LEFT JOIN a.mission m
           LEFT JOIN a.amr r
           WHERE a.amrHistoryCreatedAt >= :missionStartedAt
           AND a.amrHistoryCreatedAt <= :missionFinishedAt
           """)
    List<AmrHistory> findMissionStartedAtBetween(@Param("missionStartedAt") LocalDateTime missionStartedAt, @Param("missionFinishedAt") LocalDateTime missionFinishedAt);

}