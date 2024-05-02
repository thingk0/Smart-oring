package info.smartfactory.domain.mission.service;

import info.smartfactory.domain.mission.entity.Mission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, Mission> kafkaTemplate;

    public void create(Mission mission) {
        this.kafkaTemplate.send("mission", mission);
        System.out.println("Mission sent to Kafka: " + mission);
    }
}
