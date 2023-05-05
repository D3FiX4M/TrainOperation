package d3fix4m.ru.train_operation_test.service;

import d3fix4m.ru.train_operation_test.domain.entity.Wagon;
import d3fix4m.ru.train_operation_test.payload.WagonDTO;

import java.util.List;
import java.util.UUID;

public interface WagonService {

    Wagon getEntityById(Long id);

    WagonDTO get(Long id);

    List<WagonDTO> getAll();

    WagonDTO create(WagonDTO dto);

    WagonDTO update(Long id, WagonDTO dto);

    void delete(Long id);
}
