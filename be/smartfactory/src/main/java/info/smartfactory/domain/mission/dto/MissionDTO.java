package info.smartfactory.domain.mission.dto;


import info.smartfactory.domain.mission.entity.Submission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MissionDTO {
    private Long id;
    private LocalDateTime missionCreatedAt;
    private LocalDateTime missionFinishedAt;
    private List<Submission> submissionList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

