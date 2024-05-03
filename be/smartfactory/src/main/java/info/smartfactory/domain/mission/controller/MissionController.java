package info.smartfactory.domain.mission.controller;

import info.smartfactory.domain.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
public class MissionController {

    final private MissionService missionService;

    @GetMapping("/{missionId}")
    public String getMissionInfo() {
        missionService.getMissionInfo();
        return "missionInfo";
    }
}
