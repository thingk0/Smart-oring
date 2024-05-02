package info.smartfactory.domain.bottleneck.service;

import info.smartfactory.domain.bottleneck.dto.BottleneckDto;
import info.smartfactory.domain.bottleneck.dto.request.AddBottleneckRequest;
import info.smartfactory.domain.bottleneck.entity.Bottleneck;
import info.smartfactory.domain.bottleneck.repository.BottleneckRepository;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.node.dto.request.AddMapRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BottleneckService {
    private final BottleneckRepository bottleneckRepository;
    private final MissionService missionService;

    public List<Bottleneck> getBottleneckData(){
        List<Bottleneck> bottlenecks = bottleneckRepository.findAll();
        return bottlenecks;
    }

    public void addBottleneckData(AddBottleneckRequest request) {
        Mission mission = missionService.findById(request.getMissionId());
        Bottleneck bottleneck = Bottleneck.createBottleneck(request.getX_coordinate(), request.getY_coordinate(), mission, request.getBottleneckCreatedAt());
        bottleneckRepository.save(bottleneck);
    }
}
