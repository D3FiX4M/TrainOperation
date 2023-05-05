package d3fix4m.ru.train_operation_test.service;

import d3fix4m.ru.train_operation_test.domain.entity.PlacedWagon;
import d3fix4m.ru.train_operation_test.payload.PlacedWagonDTO;
import d3fix4m.ru.train_operation_test.payload.PlacedWagonsFullInfo;

import java.util.List;
import java.util.UUID;

public interface PlacedWagonService {

    PlacedWagon getEntityById(Long id);

    PlacedWagonDTO get(Long id);

    List<PlacedWagonDTO> getAll();

    PlacedWagonDTO create(PlacedWagonDTO dto);

    PlacedWagonDTO update(Long id, PlacedWagonDTO dto);

    void delete(Long id);

    List<PlacedWagonsFullInfo> getFullInfo();
}
