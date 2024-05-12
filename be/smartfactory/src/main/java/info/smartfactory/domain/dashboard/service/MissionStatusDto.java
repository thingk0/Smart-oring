package info.smartfactory.domain.dashboard.service;

import info.smartfactory.domain.mission.entity.Mission;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionStatusDto {
    private Mission mission;
    private boolean hasError;

}
