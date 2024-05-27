package info.smartfactory.domain.bottleneck.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorDto {

    private Integer xCoordinate;
    private Integer yCoordinate;
    private Long missionId;
    private Long amrId;

}
