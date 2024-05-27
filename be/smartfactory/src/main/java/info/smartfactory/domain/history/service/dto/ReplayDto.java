package info.smartfactory.domain.history.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import info.smartfactory.domain.history.service.RealtimeAmrDto;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class ReplayDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime time;

    List<RealtimeAmrDto> amrHistoryDtoList;
}
