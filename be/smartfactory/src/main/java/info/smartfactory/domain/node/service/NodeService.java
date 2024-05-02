package info.smartfactory.domain.node.service;

import info.smartfactory.domain.node.dto.ChargerDto;
import info.smartfactory.domain.node.dto.DestinationDto;
import info.smartfactory.domain.node.dto.MapData;
import info.smartfactory.domain.node.dto.StorageDto;
import info.smartfactory.domain.node.dto.request.MapAddRequest;
import info.smartfactory.domain.node.entity.constant.NodeType;
import info.smartfactory.domain.node.entity.type.Charger;
import info.smartfactory.domain.node.entity.type.Destination;
import info.smartfactory.domain.node.entity.type.Storage;
import info.smartfactory.domain.node.repository.ChargerRepository;
import info.smartfactory.domain.node.repository.DestinationRepository;
import info.smartfactory.domain.node.repository.NodeRepository;
import info.smartfactory.domain.node.repository.StorageRepository;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 노드 정보를 기반으로 맵 데이터를 관리. 맵에 노드(충전소, 목적지, 저장소)를 배치하고 관련 데이터를 DTO로 변환하여 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class NodeService {

    private final NodeRepository nodeRepository;
    private final StorageRepository storageRepository;
    private final ChargerRepository chargerRepository;
    private final DestinationRepository destinationRepository;

    private static final int MAP_WIDTH = 100;
    private static final int MAP_HEIGHT = 50;
    private static final int[] DX = {0, 1, 0, -1};
    private static final int[] DY = {-1, 0, 1, 0};


    /**
     * 현재 데이터베이스에 저장된 노드 정보를 이용하여 맵 데이터를 생성하고 반환합니다.
     *
     * @return 생성된 맵 데이터를 포함하는 MapData 객체.
     */
    public MapData getMapData() {
        String[][][] map = new String[MAP_WIDTH][MAP_HEIGHT][2];
        loadAndMapNodes(map);

        List<ChargerDto> chargerDtos = new ArrayList<>();
        List<DestinationDto> destinationDtos = new ArrayList<>();
        List<StorageDto> storageDtos = new ArrayList<>();

        traverseAndProcessMap(map, chargerDtos, destinationDtos, storageDtos);
        return new MapData(chargerDtos, destinationDtos, storageDtos);
    }


    /**
     * 데이터베이스에서 노드 객체를 조회하여 각 노드 타입에 따라 맵에 정보를 업데이트합니다.
     *
     * @param map 노드 정보를 업데이트할 맵 배열.
     */
    private void loadAndMapNodes(String[][][] map) {
        nodeRepository.findAll().forEach(node -> {
            if (node instanceof Charger charger) {
                charger.updateMap(map);
            } else if (node instanceof Destination destination) {
                destination.updateMap(map);
            } else if (node instanceof Storage storage) {
                storage.updateMap(map);
            }
        });
    }


    /**
     * BFS를 사용하여 맵을 순회하고, 각 노드 타입에 따라 DTO를 생성합니다.
     *
     * @param map             맵 데이터.
     * @param chargerDtos     충전소 DTO 리스트.
     * @param destinationDtos 목적지 DTO 리스트.
     * @param storageDtos     저장소 DTO 리스트.
     */
    private void traverseAndProcessMap(
        String[][][] map,
        List<ChargerDto> chargerDtos,
        List<DestinationDto> destinationDtos,
        List<StorageDto> storageDtos
    ) {
        boolean[][] visited = new boolean[MAP_WIDTH][MAP_HEIGHT];
        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0, 0});

        while (!queue.isEmpty()) {
            int[] coordinates = queue.poll();
            int x = coordinates[0];
            int y = coordinates[1];

            for (int i = 0; i < 4; i++) {
                int nx = x + DX[i];
                int ny = y + DY[i];

                if (isNodeTypeValid(nx, ny) && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});

                    String nodeType = map[nx][ny][0];
                    if (nodeType != null && isNodeTypeValid(nodeType)) {
                        createNodeDtosFromMap(map, visited, nx, ny, nodeType, chargerDtos, destinationDtos, storageDtos);
                    }
                }
            }
        }
    }


    /**
     * 주어진 위치와 노드 타입에 따라 맵에서 DTO를 생성하고 리스트에 추가합니다.
     *
     * @param map             맵 데이터.
     * @param visited         방문 여부를 기록하는 배열.
     * @param x               현재 x 좌표.
     * @param y               현재 y 좌표.
     * @param nodeType        현재 노드의 타입.
     * @param chargerDtos     충전소 DTO 리스트.
     * @param destinationDtos 목적지 DTO 리스트.
     * @param storageDtos     저장소 DTO 리스트.
     */
    private void createNodeDtosFromMap(
        String[][][] map, boolean[][] visited, int x, int y, String nodeType,
        List<ChargerDto> chargerDtos,
        List<DestinationDto> destinationDtos,
        List<StorageDto> storageDtos
    ) {
        int[] startCoordinates = {x, y};
        int[] endCoordinates = expandAndFindBoundaryOfNodeType(map, visited, x, y, nodeType);

        switch (nodeType) {
            case NodeType.CHARGER -> chargerDtos.add(new ChargerDto(map[x][y][1], startCoordinates, endCoordinates));
            case NodeType.DESTINATION -> destinationDtos.add(new DestinationDto(map[x][y][1], startCoordinates, endCoordinates));
            case NodeType.STORAGE -> storageDtos.add(new StorageDto(map[x][y][1], startCoordinates, endCoordinates));
        }
    }


    /**
     * 주어진 좌표가 맵의 유효 범위 내에 있는지 확인합니다.
     *
     * @param x 검사할 x 좌표.
     * @param y 검사할 y 좌표.
     * @return 좌표가 유효하면 true, 그렇지 않으면 false.
     */
    private boolean isNodeTypeValid(int x, int y) {
        return 0 <= x && x < MAP_WIDTH && 0 <= y && y < MAP_HEIGHT;
    }


    /**
     * 노드 타입이 유효한지 확인합니다.
     *
     * @param nodeType 검사할 노드 타입.
     * @return 노드 타입이 유효하면 true, 그렇지 않으면 false.
     */
    private boolean isNodeTypeValid(String nodeType) {
        return nodeType.equals(NodeType.STORAGE) ||
            nodeType.equals(NodeType.CHARGER) ||
            nodeType.equals(NodeType.DESTINATION);
    }


    /**
     * 노드 타입에 따라 영역을 확장하고 끝 좌표를 찾습니다.
     *
     * @param map      맵 데이터.
     * @param visited  방문 여부를 기록하는 배열.
     * @param x        시작 x 좌표.
     * @param y        시작 y 좌표.
     * @param nodeType 노드 타입.
     * @return 확장된 영역의 끝 좌표 배열.
     */
    private int[] expandAndFindBoundaryOfNodeType(
        String[][][] map, boolean[][] visited, int x, int y, String nodeType
    ) {
        int nx = x;
        int ny = y;

        while (nx < MAP_WIDTH && map[nx][y][0] != null && map[nx][y][0].equals(nodeType)) {
            visited[nx][y] = true;
            nx++;
        }

        while (ny < MAP_HEIGHT && map[x][ny][0] != null && map[x][ny][0].equals(nodeType)) {
            visited[x][ny] = true;
            ny++;
        }

        return new int[]{nx - 1, ny - 1};
    }

    /**
     * 데이터베이스에서 모든 노드 데이터를 삭제합니다.
     */
    public void clearMapData() {
        storageRepository.deleteAll();
        chargerRepository.deleteAll();
        destinationRepository.deleteAll();
    }

    /**
     * 새로운 노드를 데이터베이스에 추가합니다.
     *
     * @param request 노드 추가 요청 정보를 담은 MapAddRequest 객체.
     */
    public void addNode(MapAddRequest request) {
        switch (request.type()) {
            case NodeType.CHARGER -> chargerRepository.save(Charger.from(request));
            case NodeType.DESTINATION -> destinationRepository.save(Destination.from(request));
            case NodeType.STORAGE -> storageRepository.save(Storage.from(request));
        }
    }
}