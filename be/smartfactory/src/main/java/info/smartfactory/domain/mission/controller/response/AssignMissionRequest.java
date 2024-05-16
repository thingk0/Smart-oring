package info.smartfactory.domain.mission.controller.response;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
public class AssignMissionRequest {
    List<Integer[]> selectedNodeList;
}
