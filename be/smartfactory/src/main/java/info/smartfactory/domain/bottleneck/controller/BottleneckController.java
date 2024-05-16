package info.smartfactory.domain.bottleneck.controller;

import info.smartfactory.domain.bottleneck.dto.request.AddBottleneckRequest;
import info.smartfactory.domain.bottleneck.dto.request.BottleneckMapRequest;
import info.smartfactory.domain.bottleneck.entity.Bottleneck;
import info.smartfactory.domain.bottleneck.service.BottleneckMapDto;
import info.smartfactory.domain.bottleneck.service.BottleneckService;
import info.smartfactory.global.result.ResultResponse;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BottleneckController {

    private final BottleneckService bottleneckService;

    @GetMapping("/bottleneck")
    public ResponseEntity<ResultResponse<List<Bottleneck>>> bottleneckData() {
        List<Bottleneck> data = bottleneckService.getBottleneckData();
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), data));
    }

    @GetMapping("/bottleneck/map")
    public ResponseEntity<?> bottleneckMapData(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) String missionType
            ) {
        BottleneckMapRequest request =  new BottleneckMapRequest(startTime, endTime, missionType);

        List<BottleneckMapDto> data = bottleneckService.getBottleneckMapData(request);
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), data));
    }

    @PostMapping("/bottleneck/add")
    public ResponseEntity<ResultResponse<Bottleneck>> addBottleneckData(@RequestBody @Valid AddBottleneckRequest request) {
        bottleneckService.saveBottleneckData(request);
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString()));
    }

}
