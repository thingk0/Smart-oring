package info.smartfactory.domain.bottleneck.controller;

import info.smartfactory.domain.bottleneck.dto.request.AddBottleneckRequest;
import info.smartfactory.domain.bottleneck.entity.Bottleneck;
import info.smartfactory.domain.bottleneck.service.BottleneckService;
import info.smartfactory.domain.node.dto.request.AddMapRequest;
import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.global.result.ResultResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BottleneckController {
    private final BottleneckService bottleneckService;

    @GetMapping("/bottleneck")
    public ResponseEntity<ResultResponse<List<Bottleneck>>> mapData() {
        List<Bottleneck> data = bottleneckService.getBottleneckData();
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), data));
    }

    @PostMapping("/bottleneck/add")
    public ResponseEntity<ResultResponse<Node>> addMapData(@RequestBody @Valid AddBottleneckRequest request) {
        bottleneckService.addBottleneckData(request);
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString()));
    }
}
