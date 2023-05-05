package d3fix4m.ru.train_operation_test.domain.mapper;

import d3fix4m.ru.train_operation_test.domain.entity.PlacedWagon;
import d3fix4m.ru.train_operation_test.payload.PlacedWagonDTO;
import org.springframework.stereotype.Component;

@Component
public class PlacedWagonMapper {

    public PlacedWagonDTO toDTO(PlacedWagon entity) {
        return new PlacedWagonDTO(
                entity.getId(),
                entity.getWagon().getId(),
                entity.getPosition(),
                entity.getCargo() != null ? entity.getCargo().getId() : null,
                entity.getStationPath() != null ? entity.getStationPath().getId() : null
        );
    }
}
