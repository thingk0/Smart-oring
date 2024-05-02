package info.smartfactory.domain.amr.service;

import info.smartfactory.domain.amr.constant.AmrStatus;
import info.smartfactory.domain.amr.entity.Amr;
import info.smartfactory.domain.mission.entity.Mission;
import lombok.Data;

@Data
public class AmrDto {
	private Long id;
	private Mission mission;
	private String amrCode;
	private AmrStatus amrStatus;

	static AmrDto from(Amr amr) {
		AmrDto amrDto = new AmrDto();
		amrDto.setId(amr.getId());
		amrDto.setMission(amr.getMission());
		amrDto.setAmrCode(amr.getAmrCode());
		amrDto.setAmrStatus(amr.getAmrStatus());
		return amrDto;
	}
}
