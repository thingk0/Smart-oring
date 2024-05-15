package info.smartfactory.domain.mission.repository.custom;

import static info.smartfactory.domain.amr.entity.QAmr.amr;
import static info.smartfactory.domain.history.entity.QAmrHistory.amrHistory;
import static info.smartfactory.domain.mission.entity.QMission.mission;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import info.smartfactory.domain.mission.service.dto.MissionHistoryDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public Page<MissionHistoryDto> fetchMissionHistories(Pageable pageable,
                                                         List<String> amrCode,
                                                         LocalDateTime startTime,
                                                         LocalDateTime endTime,
                                                         Integer bottleneckSeconds
    ) {
        JPAQuery<MissionHistoryDto> contentQuery = createMissionHistoryQuery(amrCode, startTime, endTime, bottleneckSeconds);
        List<MissionHistoryDto> content = getQueryContent(pageable, contentQuery);
        JPAQuery<Long> countQuery = createCountQuery(amrCode, startTime, endTime, bottleneckSeconds);
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private JPAQuery<MissionHistoryDto> createMissionHistoryQuery(List<String> amrCode,
                                                                  LocalDateTime startTime,
                                                                  LocalDateTime endTime,
                                                                  Integer bottleneckSeconds
    ) {

        return query.select(getHistoryDtoConstructorExpression())
                    .from(mission)
                    .leftJoin(amrHistory).on(amrHistory.mission.id.eq(mission.id))
                    .leftJoin(amr).on(amrHistory.amr.id.eq(amr.id))
                    .where(commonConditions(amrCode, startTime, endTime, bottleneckSeconds))
                    .orderBy(mission.missionFinishedAt.desc());
    }

    private JPAQuery<Long> createCountQuery(List<String> amrCode,
                                            LocalDateTime startTime,
                                            LocalDateTime endTime,
                                            Integer bottleneckSeconds
    ) {
        return query.select(mission.count())
                    .from(amrHistory)
                    .leftJoin(amrHistory.mission, mission)
                    .leftJoin(amrHistory.amr, amr)
                    .where(commonConditions(amrCode, startTime, endTime, bottleneckSeconds));
    }

    private List<MissionHistoryDto> getQueryContent(
        Pageable pageable, JPAQuery<MissionHistoryDto> query
    ) {
        return query.offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
    }

    private static ConstructorExpression<MissionHistoryDto> getHistoryDtoConstructorExpression() {
        return Projections.constructor(MissionHistoryDto.class,
                                       mission.id,
                                       amr.id,
                                       amr.amrCode,
                                       Expressions.numberTemplate(Integer.class,
                                                                  "TIMESTAMPDIFF(SECOND, {0}, {1})",
                                                                  Expressions.dateTimeTemplate(LocalDateTime.class,
                                                                                               "DATE_ADD({0}, INTERVAL {1} SECOND)",
                                                                                               mission.missionStartedAt,
                                                                                               mission.missionEstimatedTime),
                                                                  mission.missionFinishedAt),
                                       mission.missionStartedAt,
                                       mission.missionFinishedAt
        );
    }

    private BooleanExpression commonConditions(List<String> amrCodes,
                                               LocalDateTime startTime,
                                               LocalDateTime endTime,
                                               Integer bottleneckSeconds) {

        BooleanExpression conditions = mission.missionFinishedAt.isNotNull();
        if (startTime != null) {
            conditions = conditions.and(mission.missionStartedAt.goe(startTime));
        }

        if (endTime != null) {
            conditions = conditions.and(mission.missionFinishedAt.loe(endTime));
        }

        if (amrCodes != null && !amrCodes.isEmpty()) {
            conditions = conditions.and(amr.amrCode.in(amrCodes));
        }

        if (bottleneckSeconds != null) {
            conditions = conditions.and(Expressions.numberTemplate(Integer.class,
                                                                   "TIMESTAMPDIFF(SECOND, {0}, {1})",
                                                                   Expressions.dateTimeTemplate(LocalDateTime.class,
                                                                                                "DATE_ADD({0}, INTERVAL {1} SECOND)",
                                                                                                mission.missionStartedAt,
                                                                                                mission.missionEstimatedTime),
                                                                   mission.missionFinishedAt)
                                                   .loe(bottleneckSeconds));
        }
        return conditions;
    }
}
