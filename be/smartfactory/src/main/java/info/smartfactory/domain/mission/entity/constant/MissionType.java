package info.smartfactory.domain.mission.entity.constant;

public enum MissionType {
    STORAGE_TO_STORAGE, // 자재창고 -> 자재창고
    STORAGE_TO_CONVEYOR, // 자재창고 -> 컨베이어 벨트
    CONVEYOR_TO_DESTINATION //컨베이어벨트 -> 완성 창고
}
