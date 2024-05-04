package info.smartfactory.domain.history.constant;

public enum AmrStatus {
    IDLE,           // 대기 상태
    MOVING,         // 이동 중
    CHARGING,       // 충전 중
    PICKUP,         // 물류 픽업 중
    DELIVERY,       // 물류 배송 중
    ERROR           // 에러 발생
}