package info.smartfactory.domain.node.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.smartfactory.domain.node.service.NodeService;
import info.smartfactory.domain.node.service.dto.NodeServiceDto;
import info.smartfactory.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map/simul")
public class NodeSimulController {

    private final NodeService nodeService;

    @GetMapping
    public ResultResponse<List<NodeServiceDto>> getMap() {
        List<NodeServiceDto> map = nodeService.getMap();
        return ResultResponse.res(HttpStatus.OK, "success", map);
    }

}
