package info.smartfactory.domain.node.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargerDto {
    private int direction;
    private int [] start;
    private int [] end;
}
