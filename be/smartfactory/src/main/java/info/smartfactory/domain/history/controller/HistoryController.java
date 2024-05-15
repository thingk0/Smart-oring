package info.smartfactory.domain.history.controller;

import java.util.List;

import info.smartfactory.domain.history.service.dto.ReplayDto;
import info.smartfactory.domain.history.service.RealtimeAmrDto;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import info.smartfactory.domain.history.dto.BatchAmrInfoRedisDto;
import info.smartfactory.domain.history.service.HistoryService;
import info.smartfactory.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HistoryController {

    final private HistoryService historyService;

    // amr 실시간 위치
    @Operation(summary = "현재 로봇 상태 조회")
    @GetMapping("/amr/state")
    public ResponseEntity<?> getRobotStates() throws JSONException {
        List<RealtimeAmrDto> result = historyService.getRecentRobotStates();
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), result));
    }


    // amr 전체 이력 저장
    @Operation(summary = "로봇 기록 전체 조회")
    @GetMapping("/histories/amr")
    public ResponseEntity<?> getRobotHistories() {
        List<BatchAmrInfoRedisDto> result = historyService.getRobotHistories();
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), result));
    }

    @Operation(summary = "분석웹 Mission페이지 미션 리플레이")
    @GetMapping("/histories/replay/{missionId}")
    public ResultResponse<?> getReplay(@PathVariable(name = "missionId") Long missionId) {
        List<ReplayDto> replayInfo = historyService.getReplay(missionId);

        return ResultResponse.res(HttpStatus.OK, "success", replayInfo);
    }

}
