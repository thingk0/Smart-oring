package info.smartfactory.domain.history.service;

import info.smartfactory.domain.amr.entity.Amr;
import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.Submission;
import org.springframework.stereotype.Service;

import info.smartfactory.domain.amr.repository.AmrRepository;
import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.history.repository.AmrHistoryRepository;
import info.smartfactory.domain.history.repository.CurrentAmrRedisRepository;
import info.smartfactory.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

	final CurrentAmrRedisRepository currentAmrRedisRepository;
	final AmrRepository amrRepository;
	final MissionRepository missionRepository;
	private final AmrHistoryRepository amrHistoryRepository;

	public void saveHistory(AmrHistoryLog amrHistoryLog) {
		//amr의 missionId로 submission 가져와서 submission 저장

		List<Submission> submissionList = missionRepository.getSubmissions(amrHistoryLog.missionId());

		List<String> nodes = new ArrayList<String>();

		 currentAmrRedisRepository.save(CurrentAmrInfoRedisDto.builder()
				 .amrId(amrHistoryLog.amrId())
				 .subMissions(nodes)
				 .amrRoute(amrHistoryLog.amrRoute())
				 .battery(amrHistoryLog.battery())
				 .xCoordinate(amrHistoryLog.xCoordinate())
				 .yCoordinate(amrHistoryLog.yCoordinate())
				 .amrStatus(amrHistoryLog.amrStatus())
				 .amrHistoryCreatedAt(amrHistoryLog.amrHistoryCreatedAt())
				 .build());
	}

	public List<CurrentAmrInfoRedisDto> getRecentRobotStates() {
		List<CurrentAmrInfoRedisDto> all = currentAmrRedisRepository.findAll();
		all.forEach(System.out::println);
		return all;
	}
}
