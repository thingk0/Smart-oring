package info.smartfactory.domain.mission.controller;

import info.smartfactory.domain.mission.entity.Submission;
import info.smartfactory.domain.mission.service.MissionService;
import info.smartfactory.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
public class MissionController {

    final private MissionService missionService;

    @GetMapping("/{missionId}")
    public ResultResponse<List<Submission>> getMissionInfo(@PathVariable(name = "missionId") Long missionId) {

        List<Submission> missionInfo = missionService.getMissionInfo(missionId);
        return ResultResponse.res(HttpStatus.OK, "succuess", missionInfo);
    }
}
