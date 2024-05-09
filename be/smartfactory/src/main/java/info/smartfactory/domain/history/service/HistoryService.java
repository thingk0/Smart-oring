package info.smartfactory.domain.history.service;

import info.smartfactory.domain.bottleneck.service.BottleneckDto;
import info.smartfactory.domain.bottleneck.service.BottleneckService;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import info.smartfactory.domain.history.repository.BatchAmrInfoRedisDto;
import info.smartfactory.domain.history.repository.CurrentAmrInfoRedisDto;
import info.smartfactory.domain.history.repository.batch.BatchAmrRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.repository.live.CurrentAmrRedisRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {

	private final RedisTemplate<String, Object> liveRedisTemplate;
	private final RedisTemplate<String, Object> batchRedisTemplate;
	private final CurrentAmrRedisRepository currentAmrRedisRepository;
	private final BatchAmrRedisRepository batchAmrRedisRepository;
	private final BottleneckService bottleneckService;

	@Autowired
	public HistoryService(@Qualifier("liveRedisTemplate") RedisTemplate<String, Object> redisTemplate,
					   @Qualifier("batchRedisTemplate") RedisTemplate<String, Object> batchRedisTemplate,
						   CurrentAmrRedisRepository currentAmrRedisRepository,
                           BatchAmrRedisRepository batchAmrRedisRepository,
						   BottleneckService bottleneckService) {
		this.liveRedisTemplate = redisTemplate;
		this.batchRedisTemplate = batchRedisTemplate;
		this.currentAmrRedisRepository = currentAmrRedisRepository;
		this.batchAmrRedisRepository = batchAmrRedisRepository;
		this.bottleneckService = bottleneckService;
	}

	public void saveHistory(AmrHistoryLog amrHistoryLog) {
		// List<Submission> submissionList = missionRepository.getSubmissions(amrHistoryLog.missionId());

		// 병목 기간 저장

		Optional<CurrentAmrInfoRedisDto> previous = currentAmrRedisRepository.findById(amrHistoryLog.amrId().toString());

		long period = 0L;

		if (previous.isPresent()) {
			CurrentAmrInfoRedisDto previousAmrInfo = previous.get();

			if (!(amrHistoryLog.amrStatus() == AmrStatus.STOPPED) && previousAmrInfo.getStopPeriod() > 0L) {
				bottleneckService.addBottleneckData(new BottleneckDto(
						amrHistoryLog.xCoordinate(),
						amrHistoryLog.yCoordinate(),
						amrHistoryLog.missionId(),
						previousAmrInfo.getStopPeriod(),
						amrHistoryLog.amrHistoryCreatedAt()
				));
			}

			if (amrHistoryLog.amrStatus() == AmrStatus.STOPPED) {
				period = previousAmrInfo.getStopPeriod() + 1L;
			}
		}else{
			if (amrHistoryLog.amrStatus() == AmrStatus.STOPPED) {
				period += 1L;
			}
		}

		// redis에 amr 현재 위치 저장

		List<String> nodes = new ArrayList<String>();

		 currentAmrRedisRepository.save(CurrentAmrInfoRedisDto.builder()
				 .amrId(amrHistoryLog.amrId())
				 .subMissions(nodes)
				 .amrRoute(amrHistoryLog.amrRoute())
				 .battery(amrHistoryLog.battery())
				 .xCoordinate(amrHistoryLog.xCoordinate())
				 .yCoordinate(amrHistoryLog.yCoordinate())
				 .amrStatus(amrHistoryLog.amrStatus())
				 .stopPeriod(period)
				 .amrHistoryCreatedAt(amrHistoryLog.amrHistoryCreatedAt())
				 .build());

		// redis에 amr 이력 저장

		batchAmrRedisRepository.save(BatchAmrInfoRedisDto.builder()
				.amrId(amrHistoryLog.amrId())
				.subMissions(nodes)
				.amrRoute(amrHistoryLog.amrRoute())
				.battery(amrHistoryLog.battery())
				.xCoordinate(amrHistoryLog.xCoordinate())
				.yCoordinate(amrHistoryLog.yCoordinate())
				.amrStatus(amrHistoryLog.amrStatus())
				.stopPeriod(period)
				.amrHistoryCreatedAt(amrHistoryLog.amrHistoryCreatedAt())
				.build());
	}

	// amr 현재 위치 가져오기

	public List<CurrentAmrInfoRedisDto> getRecentRobotStates() {
		List<CurrentAmrInfoRedisDto> all = currentAmrRedisRepository.findAll();
		all.forEach(System.out::println);
		return all;
	}

	// amr 이력 정보 가져오기

	public List<BatchAmrInfoRedisDto> getRobotHistoriess() {
		List<BatchAmrInfoRedisDto> all = batchAmrRedisRepository.findAll();
		all.forEach(System.out::println);
		return all;
	}
}
