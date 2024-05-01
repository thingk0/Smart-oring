package info.smartfactory.domain.node.controller;

import info.smartfactory.domain.node.dto.MapDto;
import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.service.NodeService;
import info.smartfactory.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import info.smartfactory.domain.node.dto.request.AddMapRequest;
import jakarta.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NodeController {
    private final NodeService nodeService;

    @GetMapping("/map")
    public ResponseEntity<ResultResponse<MapDto>> mapData() {
        MapDto mapData = nodeService.getMapData();
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), mapData));
    }

    @DeleteMapping("/map/delete")
    public ResponseEntity<ResultResponse<MapDto>> deleteMapData() {
        nodeService.deleteMapData();
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString()));
    }

    @PostMapping("/map/add")
    public ResponseEntity<ResultResponse<Node>> addMapData(@RequestBody @Valid AddMapRequest request) {
        Node node = nodeService.addMapData(request);
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), node));
    }

}
