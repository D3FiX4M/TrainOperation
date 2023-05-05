package d3fix4m.ru.train_operation_test.domain.mapper;

import d3fix4m.ru.train_operation_test.domain.entity.Station;
import d3fix4m.ru.train_operation_test.domain.entity.StationPath;
import d3fix4m.ru.train_operation_test.payload.StationDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StationMapper {

    public StationDTO toDTO(Station entity) {
        return new StationDTO(
                entity.getId(),
                entity.getName(),
                entity.getStationPaths()
                        .stream()
                        .map(StationPath::getId)
                        .collect(Collectors.toList())
        );
    }
}
