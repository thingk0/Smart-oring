package info.smartfactory.domain.history.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.smartfactory.domain.history.service.HistoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/histories")
@RequiredArgsConstructor
public class HistoryController {

	final private HistoryService historyService;

	@GetMapping("/robots/recent")
	public String getHistories() {
		historyService.getRecentRobotHistories();
		return "histories";
	}

}
