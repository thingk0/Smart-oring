package info.smartfactory.domain.node.service;

import info.smartfactory.domain.node.entity.*;
import info.smartfactory.domain.node.repository.ChargerRepository;
import info.smartfactory.domain.node.repository.DestinationRepository;
import info.smartfactory.domain.node.repository.NodeRepository;
import info.smartfactory.domain.node.repository.StorageRepository;
import info.smartfactory.domain.node.dto.ChargerDto;
import info.smartfactory.domain.node.dto.DestinationDto;
import info.smartfactory.domain.node.dto.MapDto;
import info.smartfactory.domain.node.dto.StorageDto;
import info.smartfactory.domain.node.dto.request.AddMapRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

    @Service
    @RequiredArgsConstructor
    public class NodeService {
        private final NodeRepository nodeRepository;
        private final StorageRepository storageRepository;
        private final ChargerRepository chargerRepository;
        private final DestinationRepository destinationRepository;

        static final String STORAGE = "STORAGE";
        static final String CHARGER = "CHARGER";
        static final String DESTINATION = "DESTINATION";

    static List<StorageDto> storageList;
    static List<ChargerDto> chargerList;
    static List<DestinationDto> destinationList;

    public MapDto getMapData() {
        storageList = new ArrayList<>();
        chargerList = new ArrayList<>();
        destinationList = new ArrayList<>();

        String [][][] map = new String[100][50][2];

        // DB에서 노드 가져오기
        List<Node> nodes = nodeRepository.findAllNodes();

        for (Node node : nodes) {
            if (node instanceof Storage) {
                Storage s = (Storage) node;
                map[s.getXCoordinate()][s.getYCoordinate()][0] = STORAGE;
                map[s.getXCoordinate()][s.getYCoordinate()][1] = s.getEntranceDirection().toString();
            } else if (node instanceof Charger) {
                Charger c = (Charger) node;
                map[c.getXCoordinate()][c.getYCoordinate()][0] = CHARGER;
                map[c.getXCoordinate()][c.getYCoordinate()][1] = c.getEntranceDirection().toString();
            } else if (node instanceof Destination) {
                Destination d = (Destination) node;
                map[d.getXCoordinate()][d.getYCoordinate()][0] = DESTINATION;
                map[d.getXCoordinate()][d.getYCoordinate()][1] = d.getEntranceDirection().toString();
            }
        }

        // BFS로 창고, 충전소, 도착지 찾기
        // 시작 위치와 종료 위치 Dto 리스트에 넣기 - 방향까지

        boolean [][] v = new boolean[100][50];

        bfs(v, map);

        return new MapDto(storageList, chargerList, destinationList);
    }

    static void bfs(boolean [][] v, String[][][] map) {
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

                if(map[nx][ny][0] != null &&
                        (map[nx][ny][0].equals(STORAGE) || map[nx][ny][0].equals(CHARGER) || map[nx][ny][0].equals(DESTINATION))){
                    search(map, v, nx, ny);
                }
            }
        }
    }

    static void search(String[][][] map, boolean [][] v, int x, int y) {
        String value = map[x][y][0];
        int nx = x;
        int ny = y;

        // x축 탐색
        while(nx < 100 && map[nx][y][0] != null && map[nx][y][0].equals(value)){
            nx++;
        }

        // y축 탐색
        while(ny < 50 && map[x][ny][0] != null && map[x][ny][0].equals(value)){
            ny++;
        }

        for(int i=x; i<nx; i++){
            for(int j=y; j<ny; j++){
                v[i][j] = true;
            }
        }

        nx--;
        ny--;

        if(value.equals(CHARGER)){
            chargerList.add(
                    new ChargerDto(map[x][y][1], new int[]{x, y}, new int[]{nx, ny})
            );
        }else if(value.equals(DESTINATION)){
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
    }

    public void addMapData(AddMapRequest request) {
        String type = request.getType();

        Direction direction = Direction.North;
        switch(request.getDirection()){
            case "NORTH":
                direction = Direction.North;
                break;
            case "SOUTH":
                direction = Direction.South;
                break;
            case "EAST":
                direction = Direction.East;
                break;
            case "WEST":
                direction = Direction.West;
                break;
        }

        if(type.equals("storage")) {
            Storage child = Storage.createStorage(request.getX_coordinate(), request.getY_coordinate(), direction);
            storageRepository.save(child);
        }else if(type.equals("charger")){
            Charger child = Charger.createCharger(request.getX_coordinate(), request.getY_coordinate(), direction);
            chargerRepository.save(child);
        }else if(type.equals("destination")){
            Destination child = Destination.createDestination(request.getX_coordinate(), request.getY_coordinate(), direction);
            destinationRepository.save(child);
        }
    }
}