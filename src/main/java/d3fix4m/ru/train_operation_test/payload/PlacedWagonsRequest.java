package d3fix4m.ru.train_operation_test.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlacedWagonsRequest {
    private List<Long> wagonIds = new ArrayList<>();
}
