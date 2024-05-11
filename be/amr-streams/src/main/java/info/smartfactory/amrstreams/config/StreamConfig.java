package info.smartfactory.amrstreams.config;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes.StringSerde;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

@Slf4j
@Configuration
public class StreamConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String brokerAddress;

    /**
     * Kafka 스트림 설정을 구성하는 빈을 생성합니다. 애플리케이션 ID, 부트스트랩 서버, 기본 키 및 값 Serde 클래스를 설정합니다.
     *
     * @return 커스텀 KafkaStreamsConfiguration 인스턴스
     */
    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "amr-streams-app");
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:10000,localhost:10001,localhost:10002");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddress);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, StringSerde.class.getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, StringSerde.class.getName());
        return new KafkaStreamsConfiguration(props);
    }
}

