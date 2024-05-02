package info.smartfactory.domain.node.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDto {
    private int direction;
    private int [] start;
    private int [] end;
}
