package info.smartfactory.domain.history.entity.constant;

public enum AmrStatus {
    MOVING,         // 미션 수행 중
    STOPPED,        // 미션 수행 중 멈춤
    CHARGING,       // 충전 중
    ERROR,          // 에러 발생
    DISCHARGING     // 방전
}