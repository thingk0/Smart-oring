package info.smartfactory.domain.history.service;

import info.smartfactory.domain.history.repository.BatchAmrInfoRedisDto;
import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import info.smartfactory.domain.history.repository.batch.BatchAmrRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import info.smartfactory.domain.amr.repository.AmrRepository;
import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.repository.AmrHistoryRepository;
import info.smartfactory.domain.history.repository.live.CurrentAmrRedisRepository;
import info.smartfactory.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {

	private final RedisTemplate<String, Object> liveRedisTemplate;
	private final RedisTemplate<String, Object> batchRedisTemplate;
	private final CurrentAmrRedisRepository currentAmrRedisRepository;
	private final BatchAmrRedisRepository batchAmrRedisRepository;

	@Autowired
	public HistoryService(@Qualifier("liveRedisTemplate") RedisTemplate<String, Object> redisTemplate,
					   @Qualifier("batchRedisTemplate") RedisTemplate<String, Object> batchRedisTemplate,
						   CurrentAmrRedisRepository currentAmrRedisRepository,
                           BatchAmrRedisRepository batchAmrRedisRepository) {
		this.liveRedisTemplate = redisTemplate;
		this.batchRedisTemplate = batchRedisTemplate;
		this.currentAmrRedisRepository = currentAmrRedisRepository;
		this.batchAmrRedisRepository = batchAmrRedisRepository;
	}

	public void saveHistory(AmrHistoryLog amrHistoryLog) {
		//amr의 missionId로 submission 가져와서 submission 저장

		//List<Submission> submissionList = missionRepository.getSubmissions(amrHistoryLog.missionId());

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

		batchAmrRedisRepository.save(BatchAmrInfoRedisDto.builder()
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

	public List<BatchAmrInfoRedisDto> getRobotHistoriess() {
		List<BatchAmrInfoRedisDto> all = batchAmrRedisRepository.findAll();
		all.forEach(System.out::println);
		return all;
	}
}
