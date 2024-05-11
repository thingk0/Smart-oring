package info.smartfactory.domain.mission.controller;

import info.smartfactory.domain.mission.service.MissionService;
import info.smartfactory.domain.mission.service.dto.MissionDto;
import info.smartfactory.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/{missionId}")
    public ResultResponse<MissionDto> getMissionInfo(@PathVariable(name = "missionId") Long missionId) {
        MissionDto missionInfo = missionService.getMissionInfo(missionId);
        return ResultResponse.res(HttpStatus.OK, "success", missionInfo);
    }
}
