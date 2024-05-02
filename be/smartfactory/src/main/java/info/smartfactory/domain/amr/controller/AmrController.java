package info.smartfactory.domain.amr.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.smartfactory.domain.amr.service.AmrDto;
import info.smartfactory.domain.amr.service.AmrService;
import info.smartfactory.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/amrs")
@RequiredArgsConstructor
public class AmrController {

	private final AmrService amrService;

	@GetMapping
	public ResultResponse<List<AmrDto>> getAmrs() {
		List<AmrDto> amrs = amrService.getAmrs();
		return ResultResponse.res(HttpStatus.OK, "success", amrs);
	}

}
