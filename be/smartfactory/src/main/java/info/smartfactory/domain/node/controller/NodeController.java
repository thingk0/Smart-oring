package info.smartfactory.domain.node.controller;


import info.smartfactory.domain.node.dto.MapDto;
import info.smartfactory.domain.node.service.NodeService;
import info.smartfactory.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NodeController {
    private final NodeService nodeService;

    //getMap - DB에서 node 정보 가져와서 bfs로 탐색해서 보내기
    @GetMapping("/map")
    public ResponseEntity<ResultResponse<MapDto>> mapData() {
        MapDto mapData = nodeService.getMapData();
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), mapData));
    }

}
