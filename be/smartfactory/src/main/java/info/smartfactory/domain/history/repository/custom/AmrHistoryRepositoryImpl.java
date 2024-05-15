package info.smartfactory.domain.history.repository.custom;

import static info.smartfactory.domain.amr.entity.QAmr.amr;
import static info.smartfactory.domain.history.entity.QAmrHistory.amrHistory;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import info.smartfactory.domain.mission.service.dto.MissionAnalysisDto.AmrStatusWithTime;
import info.smartfactory.domain.mission.service.dto.MissionAnalysisDto.MissionExecutionTimeAnalysisDto;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AmrHistoryRepositoryImpl implements AmrHistoryRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public MissionExecutionTimeAnalysisDto fetchMissionAnalysisInfo(Long missionId) {
        return query
            .select(Projections.constructor(MissionExecutionTimeAnalysisDto.class,
                                            amr.amrCode,
                                            amrHistory.mission.id,
                                            amrHistory.count(),
                                            amrHistory.amrStatus.when(AmrStatus.PROCESSING).then(1).otherwise(0).sum(),
                                            amrHistory.amrStatus.when(AmrStatus.BOTTLENECK).then(1).otherwise(0).sum(),
                                            amrHistory.amrStatus.when(AmrStatus.CHARGING).then(1).otherwise(0).sum(),
                                            amrHistory.amrStatus.when(AmrStatus.ERROR).then(1).otherwise(0).sum(),
                                            amrHistory.amrStatus.when(AmrStatus.DISCHARGING).then(1).otherwise(0).sum()))
            .from(amrHistory)
            .leftJoin(amrHistory.amr, amr)
            .where(amrHistory.mission.id.eq(missionId))
            .groupBy(amrHistory.mission.id)
            .fetchOne();
    }

    @Override
    public List<AmrStatusWithTime> fetchAmrStatusWithTime(Long missionId) {
        return query.select(Projections.constructor(AmrStatusWithTime.class,
                                                    amrHistory.amrStatus,
                                                    amrHistory.amrHistoryCreatedAt))
                    .from(amrHistory)
                    .where(amrHistory.mission.id.eq(missionId))
                    .orderBy(amrHistory.amrHistoryCreatedAt.asc())
                    .fetch();
    }
}
