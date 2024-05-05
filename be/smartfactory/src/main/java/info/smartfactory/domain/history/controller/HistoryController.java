package info.smartfactory.domain.history.controller;

import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import info.smartfactory.domain.node.dto.MapData;
import info.smartfactory.global.result.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.smartfactory.domain.history.service.HistoryService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HistoryController {

	final private HistoryService historyService;

	@GetMapping("/robots/state")
	public ResponseEntity<?> getRobotStates() {
		List<CurrentAmrInfoRedisDto> result = historyService.getRecentRobotStates();
		return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), result));
	}

}
