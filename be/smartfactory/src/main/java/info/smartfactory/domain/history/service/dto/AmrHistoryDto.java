package info.smartfactory.domain.history.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.smartfactory.domain.history.entity.constant.AmrStatus;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class AmrHistoryDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime updatedAt;

    Long id;

    Integer battery;

    Integer xCoordinate;

    Integer yCoordinate;

    AmrStatus amrStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime amrHistoryCreatedAt;

}
