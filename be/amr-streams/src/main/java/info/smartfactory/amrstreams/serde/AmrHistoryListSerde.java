package info.smartfactory.amrstreams.serde;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.smartfactory.amrstreams.log.AmrHistoryLog;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

@Component
public class AmrHistoryListSerde implements Serde<List<AmrHistoryLog>> {

    private final ObjectMapper objectMapper;

    public AmrHistoryListSerde(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Serializer<List<AmrHistoryLog>> serializer() {
        return (topic, data) -> {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (IOException e) {
                throw new RuntimeException("Serialization error", e);
            }
        };
    }

    @Override
    public Deserializer<List<AmrHistoryLog>> deserializer() {
        return (topic, data) -> {
            try {
                return objectMapper.readValue(data, new TypeReference<List<AmrHistoryLog>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException("Deserialization error", e);
            }
        };
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public void close() {
    }
}
