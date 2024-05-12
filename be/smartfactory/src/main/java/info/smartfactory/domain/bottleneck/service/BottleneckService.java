package info.smartfactory.domain.bottleneck.service;

import info.smartfactory.domain.bottleneck.entity.Bottleneck;
import info.smartfactory.domain.bottleneck.repository.BottleneckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BottleneckService {
    private final BottleneckRepository bottleneckRepository;

    public List<Bottleneck> getBottleneckData(){
        List<Bottleneck> bottlenecks = bottleneckRepository.findAll();
        return bottlenecks;
    }

    public BottleneckMapDto[][] getBottleneckMapData() {
        List<Bottleneck> bottleneckList = bottleneckRepository.findAll();

        BottleneckMapDto[][] bottleneckMap = new BottleneckMapDto[500][1000];

        for(int i = 0; i < 500; i++){
            for(int j = 0; j < 1000; j++){
                bottleneckMap[i][j] = new BottleneckMapDto(0L, new ArrayList<BottleneckDto>());
            }
        }

        for(Bottleneck bottleneck : bottleneckList){
            bottleneckMap[bottleneck.getXCoordinate()][bottleneck.getYCoordinate()]
                    .setBottleneckNum(bottleneckMap[bottleneck.getXCoordinate()][bottleneck.getYCoordinate()].getBottleneckNum()+1);

            bottleneckMap[bottleneck.getXCoordinate()][bottleneck.getYCoordinate()]
                .getBottleneckList().add(BottleneckDto.builder()
                                                      .missionId(bottleneck.getMission().getId())
                                                      .amrId(bottleneck.getMission().getAmr().getId())
                                                      .xCoordinate(bottleneck.getXCoordinate())
                                                      .yCoordinate(bottleneck.getYCoordinate())
                                                      .bottleneckPeriod(bottleneck.getBottleneckPeriod())
                                                      .bottleneckCreatedAt(bottleneck.getBottleneckCreatedAt())
                                                      .build());
        }

        return bottleneckMap;
    }

    public void addBottleneckData(BottleneckDto bottleneckDto) {
        Bottleneck bottleneck = Bottleneck.createBottleneck(bottleneckDto.getXCoordinate(), bottleneckDto.getYCoordinate(),
                                                            bottleneckDto.getBottleneckPeriod(), bottleneckDto.getBottleneckCreatedAt());
        bottleneckRepository.save(bottleneck);
    }
}
