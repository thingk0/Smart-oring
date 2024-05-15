package info.smartfactory.domain.history.service;

import java.time.LocalDateTime;
import java.util.*;

import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.history.service.Mapper.CurrentToRealAmrMapper;
import info.smartfactory.domain.history.service.Mapper.RealtimeAmrMapper;
import info.smartfactory.domain.history.service.dto.AmrHistoryDto;
import info.smartfactory.domain.history.service.dto.ReplayDto;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import info.smartfactory.domain.bottleneck.service.BottleneckDto;
import info.smartfactory.domain.bottleneck.service.BottleneckService;
import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import info.smartfactory.domain.history.repository.AmrHistoryRepository;
import info.smartfactory.domain.history.dto.BatchAmrInfoRedisDto;
import info.smartfactory.domain.history.dto.CurrentAmrInfoRedisDto;
import info.smartfactory.domain.history.repository.batch.BatchAmrRedisRepository;
import info.smartfactory.domain.history.repository.live.CurrentAmrRedisRepository;
import info.smartfactory.domain.history.service.Mapper.AmrHistoryMapper;
import info.smartfactory.domain.history.service.Mapper.CurrentAmrMapper;

@Service
public class HistoryService {

    @Qualifier("liveRedisTemplate")
    private final RedisTemplate<String, Object> liveRedisTemplate;
    @Qualifier("batchRedisTemplate")
    private final RedisTemplate<String, Object> batchRedisTemplate;
    private final CurrentAmrRedisRepository currentAmrRedisRepository;
    private final BatchAmrRedisRepository batchAmrRedisRepository;
    private final BottleneckService bottleneckService;
    private final MissionRepository missionRepository;
    private final AmrHistoryRepository amrHistoryRepository;
    private final AmrHistoryMapper amrHistoryMapper;

    public HistoryService(@Qualifier("liveRedisTemplate") RedisTemplate<String, Object> redisTemplate,
                          @Qualifier("batchRedisTemplate") RedisTemplate<String, Object> batchRedisTemplate,
                          CurrentAmrRedisRepository currentAmrRedisRepository,
                          BatchAmrRedisRepository batchAmrRedisRepository,
                          BottleneckService bottleneckService,
                          MissionRepository missionRepository,
                          AmrHistoryRepository amrHistoryRepository,
                          AmrHistoryMapper amrHistoryMapper
                          ) {
        this.liveRedisTemplate = redisTemplate;
        this.batchRedisTemplate = batchRedisTemplate;
        this.currentAmrRedisRepository = currentAmrRedisRepository;
        this.batchAmrRedisRepository = batchAmrRedisRepository;
        this.bottleneckService = bottleneckService;
        this.missionRepository = missionRepository;
        this.amrHistoryRepository = amrHistoryRepository;
        this.amrHistoryMapper = amrHistoryMapper;
    }

    public void saveHistory(AmrHistoryLog amrHistoryLog) {
        // 병목 기간 저장
        Optional<CurrentAmrInfoRedisDto> previous = currentAmrRedisRepository.findById(amrHistoryLog.amrId().toString());

        long period = 0L;

        if (previous.isPresent()) {
            CurrentAmrInfoRedisDto previousAmrInfo = previous.get();

            if (!(amrHistoryLog.amrStatus() == AmrStatus.BOTTLENECK) && previousAmrInfo.getStopPeriod() > 0L) {
                bottleneckService.addBottleneckData(BottleneckDto.builder()
                                                                 .missionId(amrHistoryLog.missionId())
                                                                 .amrId(amrHistoryLog.amrId())
                                                                 .xCoordinate(amrHistoryLog.xCoordinate())
                                                                 .yCoordinate(amrHistoryLog.yCoordinate())
                                                                 .bottleneckPeriod(previousAmrInfo.getStopPeriod())
                                                                 .bottleneckCreatedAt(amrHistoryLog.amrHistoryCreatedAt())
                                                                 .build());
            }

            if (amrHistoryLog.amrStatus() == AmrStatus.BOTTLENECK) {
                period = previousAmrInfo.getStopPeriod() + 1L;
            }
        } else {
            if (amrHistoryLog.amrStatus() == AmrStatus.BOTTLENECK) {
                period += 1L;
            }
        }

        // redis에 amr 실시간 위치 저장

        RealtimeAmrDto kafkaDto = RealtimeAmrMapper.INSTANCE.mapToRedisDto(amrHistoryLog);

        RealtimeAmrMapper.INSTANCE.setStopPeriod(amrHistoryLog, kafkaDto, period);


        String jsonString = getJsonStringFromList(kafkaDto.getAmrRoute());

        CurrentAmrInfoRedisDto redisDto = CurrentAmrMapper.INSTANCE.mapToRedisDto(kafkaDto);

        CurrentAmrMapper.INSTANCE.setAmrRoute(kafkaDto, redisDto, jsonString);

        currentAmrRedisRepository.save(redisDto);

        // redis에 amr 이력 저장

        BatchAmrInfoRedisDto amrHistoryDto = AmrHistoryMapper.INSTANCE.mapToRedisDto(amrHistoryLog);

        AmrHistoryMapper.INSTANCE.setStopPeriod(amrHistoryLog, amrHistoryDto, period);

        batchAmrRedisRepository.save(amrHistoryDto);
    }

    // amr 현재 위치 가져오기

    public List<RealtimeAmrDto> getRecentRobotStates() throws JSONException {
        List<CurrentAmrInfoRedisDto> all = currentAmrRedisRepository.findAll();

        List<RealtimeAmrDto> result = new ArrayList<RealtimeAmrDto>();

        for (CurrentAmrInfoRedisDto dto : all) {
            RealtimeAmrDto realtimeDto = CurrentToRealAmrMapper.INSTANCE.mapToRedisDto(dto);
             List<Integer[]> routeList = parseJsonStringToList(dto.getAmrRouteJson());
            CurrentToRealAmrMapper.INSTANCE.setAmrRoute(dto, realtimeDto, routeList);
            result.add(realtimeDto);
        }

        return result;
    }

    // amr 이력 정보 가져오기

    public List<BatchAmrInfoRedisDto> getRobotHistories() {
        List<BatchAmrInfoRedisDto> all = batchAmrRedisRepository.findAll();
        all.forEach(System.out::println);
        return all;
    }

    public static String getJsonStringFromList(List<Integer[]> list) {
        JSONArray jsonArray = new JSONArray();
        for (Integer[] array : list) {
            JSONArray innerArray = new JSONArray();
            for (Integer item : array) {
                innerArray.put(item);
            }
            jsonArray.put(innerArray);
        }
        return jsonArray.toString();
    }

    public static List<Integer[]> parseJsonStringToList(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<Integer[]> resultList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray innerJsonArray = jsonArray.getJSONArray(i);
            Integer[] array = new Integer[innerJsonArray.length()];

            for (int j = 0; j < innerJsonArray.length(); j++) {
                array[j] = innerJsonArray.getInt(j);
            }

            resultList.add(array);
        }
        return resultList;
    }

    public List<ReplayDto> getReplay(Long missionId) {

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Entity not found with ID : " + missionId));

        LocalDateTime missionStartedAt = mission.getMissionStartedAt();
        LocalDateTime missionFinishedAt = mission.getMissionFinishedAt();

        if(missionStartedAt==null || missionFinishedAt==null) {
            throw new RuntimeException("Mission startedAt or finishedAt is null : " + missionId);
        }

        List<AmrHistory> resultList = amrHistoryRepository.findMissionStartedAtBetween(missionStartedAt, missionFinishedAt);


        System.out.println("#######################################################");
        for(AmrHistory amrHistory : resultList) {
            System.out.println(amrHistory.toString());
        }

        //key에 대한 값을 저장하기
        Map<LocalDateTime, List<AmrHistoryDto>> map2 = new HashMap<>();

        for(AmrHistory amrHistory : resultList) {
            AmrHistoryDto dto = amrHistoryMapper.toDto(amrHistory);

            map2.putIfAbsent(dto.getAmrHistoryCreatedAt(), new ArrayList<>());
            map2.get(dto.getAmrHistoryCreatedAt()).add(dto);

        }

        // return 값
        List<ReplayDto> replayDtoList = new ArrayList<>();

        Set<LocalDateTime> localDateTimes = map2.keySet();

        //keySet Sort
        TreeSet<LocalDateTime> sortedSet = new TreeSet<>(localDateTimes);

        for(LocalDateTime localDateTime : sortedSet) {
            List<AmrHistoryDto> amrHistoryDto = map2.get(localDateTime);

            ReplayDto replayDto = new ReplayDto(localDateTime, amrHistoryDto);
            replayDtoList.add(replayDto);
        }

        return replayDtoList;
    }
}
