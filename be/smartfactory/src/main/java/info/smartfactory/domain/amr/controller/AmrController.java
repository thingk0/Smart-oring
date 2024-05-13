package info.smartfactory.domain.amr.controller;

import info.smartfactory.domain.amr.service.AmrService;
import info.smartfactory.domain.amr.service.dto.AmrInfoDto;
import info.smartfactory.global.result.ResultResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/amrs")
@RequiredArgsConstructor
public class AmrController {

    private final AmrService amrService;

    @GetMapping
    public ResultResponse<?> getAmrList() {
        List<AmrInfoDto> amrList = amrService.getAmrList();
        return ResultResponse.res(HttpStatus.OK, "success", amrList);
    }

}
