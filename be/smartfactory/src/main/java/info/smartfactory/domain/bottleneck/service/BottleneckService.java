package info.smartfactory.domain.bottleneck.service;

import info.smartfactory.domain.bottleneck.dto.request.AddBottleneckRequest;
import info.smartfactory.domain.bottleneck.entity.Bottleneck;
import info.smartfactory.domain.bottleneck.repository.BottleneckRepository;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.repository.MissionRepository;
import info.smartfactory.domain.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BottleneckService {
    private final BottleneckRepository bottleneckRepository;
    private final MissionRepository missionRepository;

    public List<Bottleneck> getBottleneckData(){
        List<Bottleneck> bottlenecks = bottleneckRepository.findAll();
        return bottlenecks;
    }

    public void addBottleneckData(AddBottleneckRequest request) {
        Optional<Mission> mission = missionRepository.findById(request.getMissionId());
        Bottleneck bottleneck = Bottleneck.createBottleneck(request.getX_coordinate(), request.getY_coordinate(), request.getBottleneckCreatedAt());
        bottleneckRepository.save(bottleneck);
    }
}
