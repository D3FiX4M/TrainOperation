package d3fix4m.ru.train_operation_test.domain.mapper;

import d3fix4m.ru.train_operation_test.domain.entity.PlacedWagon;
import d3fix4m.ru.train_operation_test.domain.entity.StationPath;
import d3fix4m.ru.train_operation_test.payload.StationPathDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StationPathMapper {

    public StationPathDTO toDTO(StationPath entity) {
        return new StationPathDTO(
                entity.getId(),
                entity.getStation().getId(),
                entity.getPlacedWagons()
                        .stream()
                        .map(PlacedWagon::getId)
                        .collect(Collectors.toList())
        );
    }
}
