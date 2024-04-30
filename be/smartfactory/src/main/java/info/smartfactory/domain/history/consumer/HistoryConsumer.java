package info.smartfactory.domain.history.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.smartfactory.domain.history.dto.AmrHistoryLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HistoryConsumer {

    private final ObjectMapper mapper;

    @KafkaListener(topics = "robot-stat", groupId = "amr-consumer-group")
    public void listen(@Payload String message) throws JsonProcessingException {
        AmrHistoryLog amrHistoryLog = mapper.readValue(message, AmrHistoryLog.class);
        log.info(amrHistoryLog.toString());
    }
}
