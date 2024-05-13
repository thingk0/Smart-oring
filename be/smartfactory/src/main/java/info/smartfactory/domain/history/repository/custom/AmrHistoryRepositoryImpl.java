package info.smartfactory.domain.history.repository.custom;

import static info.smartfactory.domain.history.entity.QAmrHistory.amrHistory;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import info.smartfactory.domain.mission.service.dto.MissionInfoDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AmrHistoryRepositoryImpl implements AmrHistoryRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public MissionInfoDto fetchMissionAnalysisInfo(Long missionId) {
        Tuple result = query
            .select(amrHistory.count(),
                    amrHistory.amrStatus.when(AmrStatus.valueOf("PROCESSING")).then(1).otherwise(0).sum(),
                    amrHistory.amrStatus.when(AmrStatus.valueOf("BOTTLENECK")).then(1).otherwise(0).sum(),
                    amrHistory.amrStatus.when(AmrStatus.valueOf("CHARGING")).then(1).otherwise(0).sum(),
                    amrHistory.amrStatus.when(AmrStatus.valueOf("ERROR")).then(1).otherwise(0).sum(),
                    amrHistory.amrStatus.when(AmrStatus.valueOf("DISCHARGING")).then(1).otherwise(0).sum())
            .from(amrHistory)
            .where(amrHistory.mission.id.eq(missionId))
            .groupBy(amrHistory.mission.id)
            .fetchOne();

        if (result != null) {
            return new MissionInfoDto(
                result.get(0, Integer.class),        // Total count
                result.get(1, Integer.class),       // PROCESSING sum
                result.get(2, Integer.class),       // BOTTLENECK sum
                result.get(3, Integer.class),       // CHARGING sum
                result.get(4, Integer.class),       // ERROR sum
                result.get(5, Integer.class),       // DISCHARGING sum
                query.select(amrHistory.amrStatus)    // List<AmrStatus>
                     .from(amrHistory)
                     .where(amrHistory.mission.id.eq(missionId))
                     .fetch()
            );
        } else {
            throw new IllegalArgumentException("미션 ID에 대한 정보가 없습니다: " + missionId);
        }
    }
}
