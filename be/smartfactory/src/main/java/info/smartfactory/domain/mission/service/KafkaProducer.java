package info.smartfactory.domain.mission.service;

import info.smartfactory.domain.mission.entity.Mission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Mission> kafkaTemplate;

    public void create(Mission mission) {
        kafkaTemplate.send("mission", mission);
        System.out.println("Mission sent to Kafka: " + mission);
    }
}
