package info.smartfactory.domain.node.service;

import info.smartfactory.domain.node.Repository.NodeRepository;
import info.smartfactory.domain.node.dto.ChargerDto;
import info.smartfactory.domain.node.dto.DestinationDto;
import info.smartfactory.domain.node.dto.MapDto;
import info.smartfactory.domain.node.dto.StorageDto;
import info.smartfactory.domain.node.entity.Charger;
import info.smartfactory.domain.node.entity.Destination;
import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.entity.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.messaging.simp.stomp.StompHeaders.DESTINATION;

@Service
@RequiredArgsConstructor
public class NodeService {
    private final NodeRepository nodeRepository;

    static boolean [][] v;
    static final int CHARGER = -1;
    static final int DESTINATION = -2;

    public MapDto getMapData() {
        int[][] map = new int[100][50];



        // DB에서 노드 가져오기
        List<Storage> storage = nodeRepository.getStorage();
        List<Charger> charger = nodeRepository.getCharger();
        List<Destination> destination = nodeRepository.getDestination();

        // map 채우기

        for (Storage s : storage) {
            Node node = s.getNode();
            map[node.getXCoordinate()][node.getYCoordinate()] = s.getEntranceDirection();
        }

        for (Charger c : charger) {
            Node node = c.getNode();
            map[node.getXCoordinate()][node.getYCoordinate()] = CHARGER;
        }

        for (Destination d : destination) {
            Node node = d.getNode();
            map[node.getXCoordinate()][node.getYCoordinate()] = DESTINATION;
        }

        // BFS로 창고, 충전소, 도착지 찾기
        // 시작 위치와 종료 위치 Dto 리스트에 넣기 - 창고는 방향까지

        List<StorageDto> storageList = new ArrayList<>();
        List<ChargerDto> chargerList = new ArrayList<>();
        List<DestinationDto> destinationList = new ArrayList<>();

        v = new boolean[100][50];

        bfs(map);

        return new MapDto(storageList, chargerList, destinationList);
    }

    // 한 노드씩 보면서 0이면 그냥 큐에 넣고 지나가기
    // 창고가 있으면 네모찾기 알고리즘?
    //
    static void bfs(int[][] map) {
        int[] dx = new int[]{0, 1, 0, -1};
        int[] dy = new int[]{-1, 0, 1, 0};

        Deque<Integer[]> q = new ArrayDeque<>();
        q.add(new Integer[]{0, 0});

        while (!q.isEmpty()) {
            Integer[] arr = q.poll();
            int x = arr[0];
            int y = arr[1];

            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 0 || nx >= 100 || ny < 0 || ny >= 50) continue;
                if (v[nx][ny]) continue;

                if (map[nx][ny] == 0) {
                    v[nx][ny] = true;
                    q.add(new Integer[]{nx, ny});
                }else if(map[nx][ny] == CHARGER){

                }else if(map[nx][ny] == DESTINATION){

                }else if(1 <= map[nx][ny] && map[nx][ny] <=4){
                    v[nx][ny] = true;
                    q.add(new Integer[]{nx, ny});
                    search(map, nx, ny);
                }
            }
        }
    }

    static void search(int[][] map, int x, int y) {
        // x축 이동
        int nx = x;
        while(true){
            v[nx][y] = true;
            if(map[nx][y] != map[x][y]) break;
        }

        // y축 이동
        while(true){

        }
    }

}