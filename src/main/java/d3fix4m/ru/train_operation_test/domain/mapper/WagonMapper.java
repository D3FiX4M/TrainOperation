package d3fix4m.ru.train_operation_test.domain.mapper;

import d3fix4m.ru.train_operation_test.domain.entity.Wagon;
import d3fix4m.ru.train_operation_test.payload.WagonDTO;
import org.springframework.stereotype.Component;

@Component
public class WagonMapper {
    public WagonDTO toDTO(Wagon entity) {
        return new WagonDTO(
                entity.getId(),
                entity.getWeight(),
                entity.getCapacity()
        );
    }
}
