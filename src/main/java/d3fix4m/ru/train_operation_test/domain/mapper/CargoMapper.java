package d3fix4m.ru.train_operation_test.domain.mapper;

import d3fix4m.ru.train_operation_test.domain.entity.Cargo;
import d3fix4m.ru.train_operation_test.payload.CargoDTO;
import org.springframework.stereotype.Component;

@Component
public class CargoMapper {

    public CargoDTO toDTO(Cargo entity) {
        return new CargoDTO(
                entity.getId(),
                entity.getName(),
                entity.getWeight()
        );
    }
}
