package info.smartfactory.domain.history.consumer;

import info.smartfactory.domain.bottleneck.dto.BottleneckDto;
import info.smartfactory.domain.bottleneck.service.BottleneckService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.smartfactory.domain.history.dto.AmrHistoryLog;
import info.smartfactory.domain.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class HistoryConsumer {

    private final ObjectMapper mapper;
    private final HistoryService historyService;
    private final BottleneckService bottleneckService;

    @KafkaListener(topics = "robot-stat", groupId = "amr-consumer-group")
    public void listen(@Payload String message) throws JsonProcessingException {
        AmrHistoryLog amrHistoryLog = mapper.readValue(message, AmrHistoryLog.class);
        log.info(amrHistoryLog.toString());
        historyService.saveHistory(amrHistoryLog);

        if(amrHistoryLog.amrStatus().equals("STOPPED")){
            bottleneckService.addBottleneckData(new BottleneckDto(amrHistoryLog.xCoordinate(), amrHistoryLog.yCoordinate(), amrHistoryLog.missionId(), amrHistoryLog.amrHistoryCreatedAt()));
        }
    }
}
