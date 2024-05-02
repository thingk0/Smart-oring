package info.smartfactory.domain.history.service;

import org.springframework.stereotype.Service;

import info.smartfactory.domain.amr.repository.AmrRepository;
import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.history.repository.AmrHistoryRepository;
import info.smartfactory.domain.history.repository.CurrentAmrRedisRepository;
import info.smartfactory.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {

	final CurrentAmrRedisRepository currentAmrRedisRepository;
	final AmrRepository amrRepository;
	final MissionRepository missionRepository;
	private final AmrHistoryRepository amrHistoryRepository;

	public void saveHistory(AmrHistoryLog amrHistoryLog) {
		// Amr amr = amrRepository.findById(amrHistoryLog.amrId()).orElseThrow();
		// Mission mission = missionRepository.findById(amrHistoryLog.missionId()).orElseThrow();
		// AmrHistory amrHistory = AmrHistory.of(
		// 	mission,
		// 	amr,
		// 	amrHistoryLog.battery(),
		// 	amrHistoryLog.xCoordinate(),
		// 	amrHistoryLog.yCoordinate()
		// );
		// amrHistoryRepository.save(amrHistory);

		// AmrHistory amrHistory = amrHistoryLog.toAmrHistory(
		// 	Mission.createMission(),
		// 	new Amr()
		// );
		// currentAmrRedisRepository.save(amrHistory);
	}

	public void getRecentRobotHistories() {
		Iterable<AmrHistory> all = currentAmrRedisRepository.findAll();
		all.forEach(System.out::println);
	}
}
