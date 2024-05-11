package info.smartfactory.domain.bottleneck.controller;

import info.smartfactory.domain.bottleneck.entity.Bottleneck;
import info.smartfactory.domain.bottleneck.service.BottleneckMapDto;
import info.smartfactory.domain.bottleneck.service.BottleneckService;
import info.smartfactory.global.result.ResultResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> bottleneckMapData() {
        BottleneckMapDto[][] data = bottleneckService.getBottleneckMapData();
        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString(), data));
    }

//    @PostMapping("/bottleneck/add")
//    public ResponseEntity<ResultResponse<Bottleneck>> addBottleneckData(@RequestBody @Valid AddBottleneckRequest request) {
//        bottleneckService.addBottleneckData(request);
//        return ResponseEntity.ok(ResultResponse.res(HttpStatus.OK, HttpStatus.OK.toString()));
//    }

}
