package info.smartfactory.domain.bottleneck.service;

import info.smartfactory.domain.bottleneck.dto.BottleneckDto;
import info.smartfactory.domain.bottleneck.dto.request.AddBottleneckRequest;
import info.smartfactory.domain.bottleneck.entity.Bottleneck;
import info.smartfactory.domain.bottleneck.repository.BottleneckRepository;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BottleneckService {
    private final BottleneckRepository bottleneckRepository;

    public List<Bottleneck> getBottleneckData(){
        List<Bottleneck> bottlenecks = bottleneckRepository.findAll();
        return bottlenecks;
    }

    public void addBottleneckData(BottleneckDto bottleneckDto) {
        Bottleneck bottleneck = Bottleneck.createBottleneck(bottleneckDto.getX_coordinate(), bottleneckDto.getY_coordinate(), bottleneckDto.getBottleneck_time());
        bottleneckRepository.save(bottleneck);
    }
}
