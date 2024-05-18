package info.smartfactory.domain.bottleneck.service;

import info.smartfactory.domain.bottleneck.dto.request.AddBottleneckRequest;
import info.smartfactory.domain.bottleneck.dto.request.BottleneckMapRequest;
import info.smartfactory.domain.bottleneck.entity.Bottleneck;
import info.smartfactory.domain.bottleneck.repository.BottleneckRepository;
import info.smartfactory.domain.mission.entity.Mission;
import info.smartfactory.domain.mission.entity.constant.MissionType;
import info.smartfactory.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static info.smartfactory.domain.mission.entity.constant.MissionType.STORAGE_TO_CONVEYOR;

@Service
@RequiredArgsConstructor
public class BottleneckService {
    private final BottleneckRepository bottleneckRepository;
    private final MissionRepository missionRepository;

    public List<Bottleneck> getBottleneckData(){
        List<Bottleneck> bottlenecks = bottleneckRepository.findAll();
        return bottlenecks;
    }

    public List<BottleneckMapDto> getBottleneckMapData(BottleneckMapRequest request) {

        System.out.println("endTime"+request.getEndDate());

        List<BottleneckMapDto> bottleneckMap = new ArrayList<>();
        List<Bottleneck> bottleneckList;

        if(request.getMissionType() == null){
            if(request.getStartDate() == null && request.getEndDate() == null){
                bottleneckList = bottleneckRepository.findAll();
            }else if(request.getEndDate() == null){
                bottleneckList = bottleneckRepository.findAllByBottleneckFromStartDate(request.getStartDate());
            }else if(request.getStartDate() == null){
                bottleneckList = bottleneckRepository.findAllByBottleneckBeforeEndDate(request.getEndDate());
            }else{
                bottleneckList = bottleneckRepository.findAllByBottleneckCreatedAtBetween(request.getStartDate(), request.getEndDate());
            }
        }else{
            MissionType type;
            switch(request.getMissionType()){
                case "STORAGE_TO_STORAGE":
                    type = MissionType.STORAGE_TO_STORAGE;
                    break;
                case "STORAGE_TO_CONVEYOR":
                    type = STORAGE_TO_CONVEYOR;
                    break;
                case "CONVEYOR_TO_DESTINATION":
                    type = MissionType.CONVEYOR_TO_DESTINATION;
                    break;
                default:
                    type = STORAGE_TO_CONVEYOR;
            }

            if(request.getStartDate() == null && request.getEndDate() == null){
                bottleneckList = bottleneckRepository.findBottltneckByMissionType(type);
            }else if(request.getEndDate() == null){
                bottleneckList = bottleneckRepository.findByMissionTypeAndAfterStartDate(request.getStartDate(), type);
            }else if(request.getStartDate() == null){
                bottleneckList = bottleneckRepository.findByMissionTypeAndBeforeEndDate(request.getEndDate(), type);
            }else{
                bottleneckList = bottleneckRepository.findByMissionTypeAndDateBetween(request.getStartDate(), request.getEndDate(), type);
            }
        }


        for (Bottleneck bottleneck : bottleneckList) {
            System.out.println("Bottleneck Created At: " + bottleneck.getBottleneckCreatedAt());
        }

        for (int i = 0; i < 50; i++) {
            List<HeatmapDto> heatmapDtoList = new ArrayList<HeatmapDto>();
            for (int j = 0; j < 100; j++) {
                heatmapDtoList.add(HeatmapDto.builder().x(j).y(0).build());
            }
            bottleneckMap.add(BottleneckMapDto.builder().name(i).data(heatmapDtoList).build());
        }

        for(Bottleneck bottleneck : bottleneckList){
            bottleneckMap.get(bottleneck.getXCoordinate()).getData().get(bottleneck.getYCoordinate())
                    .setY(bottleneckMap.get(bottleneck.getXCoordinate()).getData().get(bottleneck.getYCoordinate()).getY()+1);
        }

        return bottleneckMap;
    }

    public void addBottleneckData(BottleneckDto bottleneckDto) {
        Mission mission = missionRepository.findMissionById(bottleneckDto.getMissionId());

        Bottleneck bottleneck = Bottleneck.builder()
                .xCoordinate(bottleneckDto.getXCoordinate())
                .yCoordinate(bottleneckDto.getYCoordinate())
                .mission(mission)
                .bottleneckPeriod(bottleneckDto.getBottleneckPeriod())
                .bottleneckCreatedAt(bottleneckDto.getBottleneckCreatedAt())
                .build();
        bottleneckRepository.save(bottleneck);
    }

    public void saveBottleneckData(AddBottleneckRequest request) {
        Bottleneck bottleneck = Bottleneck.builder()
                .xCoordinate(request.getXCoordinate())
                .yCoordinate(request.getYCoordinate())
                .bottleneckPeriod(request.getBottleneckPeriod())
                .bottleneckCreatedAt(request.getBottleneckTime())
                .build();
        bottleneckRepository.save(bottleneck);
    }
}
