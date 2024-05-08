package info.smartfactory.domain.mission.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.smartfactory.domain.mission.entity.Submission;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Submission}
 */
@Value
public class SubmissionDto implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime updatedAt;
    Long id;
    NodeDto arriveNode;
    Integer submissionOrder;
}