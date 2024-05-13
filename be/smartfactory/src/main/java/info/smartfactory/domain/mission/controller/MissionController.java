package info.smartfactory.domain.mission.controller;

import info.smartfactory.domain.mission.service.MissionService;
import info.smartfactory.domain.mission.service.dto.MissionDto;
import info.smartfactory.domain.mission.service.dto.MissionHistoryDto;
import info.smartfactory.domain.mission.service.dto.MissionInfoDto;
import info.smartfactory.global.result.ResultResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    public ResultResponse<?> getMissionHistory(
        @RequestParam(required = false) String amrType,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
        @RequestParam(required = false) Integer bottleneckSeconds,
        @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<MissionHistoryDto> missionHistories = missionService.getMissionHistories(pageable, amrType, startTime, endTime, bottleneckSeconds);
        return ResultResponse.res(HttpStatus.OK, "success", missionHistories);
    }

    @GetMapping("/{missionId}/analysis")
    public ResultResponse<?> getMission(@PathVariable Long missionId) {
        MissionInfoDto missionHistoryAnalysisInfo = missionService.getMissionHistoryAnalysisInfo(missionId);
        return ResultResponse.res(HttpStatus.OK, "success", missionHistoryAnalysisInfo);
    }

    @GetMapping("/{missionId}")
    public ResultResponse<MissionDto> getMissionInfo(@PathVariable(name = "missionId") Long missionId) {
        MissionDto missionInfo = missionService.getMissionInfo(missionId);
        return ResultResponse.res(HttpStatus.OK, "success", missionInfo);
    }
}
