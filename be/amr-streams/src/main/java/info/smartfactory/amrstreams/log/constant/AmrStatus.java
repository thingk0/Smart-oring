package info.smartfactory.amrstreams.log.constant;

public enum AmrStatus {
    PROCESSING,     // 미션 수행 중
    BOTTLENECK,     // 미션 수행 중 멈춤
    CHARGING,       // 충전 중
    ERROR,          // 에러 발생
    DISCHARGING     // 방전
}