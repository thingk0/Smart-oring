package info.smartfactory.domain.dashboard.controller;

import info.smartfactory.domain.dashboard.service.DashboardDto;
import info.smartfactory.domain.dashboard.service.DashboardService;
import info.smartfactory.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<?> getRealtimeData() {
        DashboardDto result = dashboardService.getRealtimeData();
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), result));
    }

}
