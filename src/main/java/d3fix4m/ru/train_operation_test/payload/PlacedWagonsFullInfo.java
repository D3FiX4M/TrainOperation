package d3fix4m.ru.train_operation_test.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlacedWagonsFullInfo {
    private Long position;
    private WagonDTO wagon;
    private CargoDTO cargo;
}
