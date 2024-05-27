package info.smartfactory.domain.node.controller;

import info.smartfactory.domain.node.dto.MapData;
import info.smartfactory.domain.node.dto.request.MapAddRequest;
import info.smartfactory.domain.node.service.NodeService;
import info.smartfactory.global.result.ResultResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NodeController {

    private final NodeService nodeService;

    @GetMapping("/map")
    public ResponseEntity<?> mapData() {
        MapData mapData = nodeService.getMapData();
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), mapData));
    }

    @DeleteMapping("/map/delete")
    public ResponseEntity<?> deleteMapData() {
        nodeService.clearMapData();
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString()));
    }

    @PostMapping("/map/add")
    public ResponseEntity<?> addMapData(@RequestBody @Valid MapAddRequest request) {
        nodeService.addNode(request);
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString()));
    }

}
