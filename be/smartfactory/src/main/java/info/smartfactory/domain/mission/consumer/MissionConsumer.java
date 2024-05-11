package info.smartfactory.domain.mission.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.smartfactory.domain.mission.service.MissionService;
import info.smartfactory.domain.mission.service.dto.MissionKafkaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MissionConsumer {

    private final ObjectMapper mapper;
    private final MissionService missionService;

    @KafkaListener(topics = "mission-complete", groupId = "mission-consumer-group")
    public void listen(@Payload String message) throws JsonProcessingException {
        MissionKafkaDto missionKafkaDto = mapper.readValue(message, MissionKafkaDto.class);

        log.info(missionKafkaDto.toString());
        missionService.completeMission(missionKafkaDto);
    }
}
