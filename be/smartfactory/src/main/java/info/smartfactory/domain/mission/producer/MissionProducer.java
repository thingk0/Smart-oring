package info.smartfactory.domain.mission.producer;

import info.smartfactory.domain.mission.dto.MissionKafkaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MissionProducer {

    private final KafkaTemplate<String, MissionKafkaDTO> kafkaTemplate;

    public void create(MissionKafkaDTO mission) {
        kafkaTemplate.send("mission", mission);
        System.out.println("Mission sent to Kafka: " + mission);
    }
}
