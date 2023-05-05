package d3fix4m.ru.train_operation_test.service;

import d3fix4m.ru.train_operation_test.domain.entity.Cargo;
import d3fix4m.ru.train_operation_test.payload.CargoDTO;

import java.util.List;
import java.util.UUID;

public interface CargoService {

    Cargo getEntityById(Long id);

    CargoDTO get(Long id);

    List<CargoDTO> getAll();

    CargoDTO create(CargoDTO dto);

    CargoDTO update(Long id, CargoDTO dto);

    void delete(Long id);
}
