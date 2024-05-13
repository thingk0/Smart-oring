package info.smartfactory.domain.mission.repository.custom;

import info.smartfactory.domain.mission.service.dto.MissionHistoryDto;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MissionRepositoryCustom {

    Page<MissionHistoryDto> fetchMissionHistories(Pageable pageable,
                                                  String amrType,
                                                  LocalDateTime startTime,
                                                  LocalDateTime endTime,
                                                  Integer bottleneckSeconds);
}
