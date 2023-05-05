package d3fix4m.ru.train_operation_test.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoDTO {
    private Long id;
    private String name;
    private Long weight;
}
