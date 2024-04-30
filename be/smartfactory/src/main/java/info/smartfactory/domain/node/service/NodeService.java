package info.smartfactory.domain.node.service;

import info.smartfactory.domain.node.Repository.ChargerRepository;
import info.smartfactory.domain.node.Repository.DestinationRepository;
import info.smartfactory.domain.node.Repository.NodeRepository;
import info.smartfactory.domain.node.Repository.StorageRepository;
import info.smartfactory.domain.node.dto.ChargerDto;
import info.smartfactory.domain.node.dto.DestinationDto;
import info.smartfactory.domain.node.dto.MapDto;
import info.smartfactory.domain.node.dto.StorageDto;
import info.smartfactory.domain.node.dto.request.AddMapRequest;
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
    private final StorageRepository storageRepository;
    private final ChargerRepository chargerRepository;
    private final DestinationRepository destinationRepository;

    static final int CHARGER = -1;
    static final int DESTINATION = -2;

    static List<StorageDto> storageList;
    static List<ChargerDto> chargerList;
    static List<DestinationDto> destinationList;



    public MapDto getMapData() {
        storageList = new ArrayList<>();
        chargerList = new ArrayList<>();
        destinationList = new ArrayList<>();

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

        boolean [][] v = new boolean[100][50];

        bfs(v, map);

        return new MapDto(storageList, chargerList, destinationList);
    }

    static void bfs(boolean [][] v, int[][] map) {
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
                }else{
                    v[nx][ny] = true;
                    q.add(new Integer[]{nx, ny});
                    search(map, v, nx, ny);
                }
            }
        }
    }

    static void search(int[][] map, boolean [][] v, int x, int y) {
        // x축 이동
        int nx = x;
        while(true){
            v[nx][y] = true;
            if(map[nx][y] != map[x][y]) break;
        }

        int ny = y;
        // y축 이동
        while(true){
            v[x][ny] = true;
            if(map[x][ny] != map[x][y]) break;
        }

        if(map[x][y] == CHARGER){
            chargerList.add(
                    new ChargerDto(new int[]{x, y}, new int[]{nx-1, ny-1})
            );
        }else if(map[x][y] == DESTINATION){
            destinationList.add(
                    new DestinationDto(new int[]{x, y}, new int[]{nx-1, ny-1})
            );
        }else{ // STORAGE
            storageList.add(
                    new StorageDto(map[x][y],new int[]{x, y}, new int[]{nx-1, ny-1})
            );
        }
    }

    public void deleteMapData() {
        nodeRepository.deleteMap();
    }

    public Node addMapData(AddMapRequest request) {
        Node node = new Node();
        node.setXCoordinate(request.getX_coordinate());
        node.setYCoordinate(request.getY_coordinate());
        nodeRepository.save(node);

        String type = request.getType();

        if(type.equals("storage")) {
            Storage child = new Storage();
            child.setEntranceDirection(request.getDirection());
            child.setNode(node);
            storageRepository.save(child);
        }else if(type.equals("charger")){
            Charger child = new Charger();
            child.setNode(node);
            chargerRepository.save(child);
        }else if(type.equals("destination")){
            Destination child = new Destination();
            child.setNode(node);
            destinationRepository.save(child);
        }
        return node;
    }
}