package info.smartfactory.domain.node.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddMapRequest {

    @NotNull(message = "x좌표는 필수 항목입니다.")
    int x_coordinate;

    @NotNull(message = "y좌표는 필수 항목입니다.")
    int y_coordinate;

    @NotNull(message = "노드 타입은 필수 항목입니다.")
    String type;

    @NotNull(message = "방향은 필수 항목입니다.")
    String direction;
}