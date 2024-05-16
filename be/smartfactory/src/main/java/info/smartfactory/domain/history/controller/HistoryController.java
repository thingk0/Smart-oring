package info.smartfactory.domain.history.controller;

import java.time.LocalDateTime;
import java.util.List;

import info.smartfactory.domain.history.service.RealtimeAmrDto;
import info.smartfactory.domain.history.service.dto.AmrHistoryDto;
import info.smartfactory.domain.history.service.dto.ReplayDto;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.format.annotation.DateTimeFormat;
import info.smartfactory.domain.history.service.RealtimeAmrDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import info.smartfactory.domain.history.dto.BatchAmrInfoRedisDto;
import info.smartfactory.domain.history.dto.CurrentAmrInfoRedisDto;
import info.smartfactory.domain.history.service.HistoryService;
import info.smartfactory.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/histories")
public class HistoryController {

    final private HistoryService historyService;

    // amr 실시간 위치
    @Operation(summary = "현재 로봇 상태 조회")
    @GetMapping("/amr/state")
    public ResponseEntity<?> getRobotStates() {
        List<RealtimeAmrDto> result = historyService.getRecentRobotStates();
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), result));
    }


    // amr 전체 이력 저장
    @Operation(summary = "로봇 기록 전체 조회")
    @GetMapping("/amr")
    public ResponseEntity<?> getRobotHistories() {
        List<BatchAmrInfoRedisDto> result = historyService.getRobotHistories();
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), result));
    }

    @Operation(summary = "분석웹 미션 전체 리플레이")
    @GetMapping("/replay/{missionId}")
    public ResultResponse<?> getReplay(@PathVariable(name = "missionId") Long missionId) {
        List<ReplayDto> replayInfo = historyService.getReplay(missionId);

        return ResultResponse.res(HttpStatus.OK, "success", replayInfo);
    }

    @Operation(summary = "분석웹 특정 병목 리플레이")
    @GetMapping("/replay/bottleneck")
    public ResultResponse<?> getReplayBottleneck(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
//        historyService.getBottleneckReplay(startTime, endTime);

        return ResultResponse.res(HttpStatus.OK, "success");
    }

}
