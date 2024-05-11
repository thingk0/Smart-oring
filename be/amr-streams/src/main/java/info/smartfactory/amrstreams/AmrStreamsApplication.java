package info.smartfactory.amrstreams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@EnableKafkaStreams
@SpringBootApplication
public class AmrStreamsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmrStreamsApplication.class, args);
    }

}
