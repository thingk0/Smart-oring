package info.smartfactory.domain.history.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import info.smartfactory.domain.history.repository.BatchAmrInfoRedisDto;
import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
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
	public ResponseEntity<?> getRobotStates() {
		List<CurrentAmrInfoRedisDto> result = historyService.getRecentRobotStates();
		return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), result));
	}


	// amr 전체 이력 저장

	@Operation(summary = "로봇 기록 전체 조회")
	@GetMapping("/histories/amr")
	public ResponseEntity<?> getRobotHistories() {
		List<BatchAmrInfoRedisDto> result = historyService.getRobotHistoriess();
		return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), result));
	}

}
