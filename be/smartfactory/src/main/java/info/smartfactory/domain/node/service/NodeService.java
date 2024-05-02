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

        static final int STORAGE = 1;
        static final int CHARGER = 2;
        static final int DESTINATION = 3;

    static List<StorageDto> storageList;
    static List<ChargerDto> chargerList;
    static List<DestinationDto> destinationList;

    public MapDto getMapData() {
        storageList = new ArrayList<>();
        chargerList = new ArrayList<>();
        destinationList = new ArrayList<>();

        int [][][] map = new int[100][50][2];

        // DB에서 노드 가져오기
        List<Storage> storage = storageRepository.getStorage();
        List<Charger> charger = chargerRepository.getCharger();
        List<Destination> destination = destinationRepository.getDestination();

        // map 채우기
        for (Storage s : storage) {
            Node node = s.getNode();
            map[node.getXCoordinate()][node.getYCoordinate()][0] = STORAGE;
            map[node.getXCoordinate()][node.getYCoordinate()][1] = s.getEntranceDirection();
        }

        for (Charger c : charger) {
            Node node = c.getNode();
            map[node.getXCoordinate()][node.getYCoordinate()][0] = CHARGER;
            map[node.getXCoordinate()][node.getYCoordinate()][1] = c.getEntranceDirection();
        }

        for (Destination d : destination) {
            Node node = d.getNode();
            map[node.getXCoordinate()][node.getYCoordinate()][0] = DESTINATION;
            map[node.getXCoordinate()][node.getYCoordinate()][1] = d.getEntranceDirection();
        }

        // BFS로 창고, 충전소, 도착지 찾기
        // 시작 위치와 종료 위치 Dto 리스트에 넣기 - 창고는 방향까지

        boolean [][] v = new boolean[100][50];

        bfs(v, map);

        return new MapDto(storageList, chargerList, destinationList);
    }

    static void bfs(boolean [][] v, int[][][] map) {
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};

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

                v[nx][ny] = true;
                q.add(new Integer[]{nx, ny});

                if(map[nx][ny][0] != 0){
                    search(map, v, nx, ny);
                }
            }
        }
    }

    static void search(int[][][] map, boolean [][] v, int x, int y) {
        int value = map[x][y][0];
        int nx = x;
        int ny = y;

        // x축 탐색
        while(nx < 100 && map[nx][y][0] == value){
            v[nx][y] = true;
            nx++;
        }


        // y축 탐색
        while(ny < 50 && map[x][ny][0] == value){
            v[x][ny] = true;
            ny++;
        }

        for(int i=x; i<nx; i++){
            for(int j=y; j<ny; j++){
                v[i][j] = true;
            }
        }

        nx--;
        ny--;

        if(value == CHARGER){
            chargerList.add(
                    new ChargerDto(map[x][y][1], new int[]{x, y}, new int[]{nx, ny})
            );
        }else if(value == DESTINATION){
            destinationList.add(
                    new DestinationDto(map[x][y][1], new int[]{x, y}, new int[]{nx, ny})
            );
        }else{ // STORAGE
            storageList.add(
                    new StorageDto(map[x][y][1],new int[]{x, y}, new int[]{nx, ny})
            );
        }
    }

    public void deleteMapData() {
        storageRepository.deleteAll();
        chargerRepository.deleteAll();
        destinationRepository.deleteAll();
        nodeRepository.deleteAll();
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
            child.setEntranceDirection(request.getDirection());
            child.setNode(node);
            chargerRepository.save(child);
        }else if(type.equals("destination")){
            Destination child = new Destination();
            child.setEntranceDirection(request.getDirection());
            child.setNode(node);
            destinationRepository.save(child);
        }
        return node;
    }
}