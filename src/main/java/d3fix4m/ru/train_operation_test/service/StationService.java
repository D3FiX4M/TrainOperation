package d3fix4m.ru.train_operation_test.service;

import d3fix4m.ru.train_operation_test.domain.entity.EChooseSide;
import d3fix4m.ru.train_operation_test.domain.entity.Station;
import d3fix4m.ru.train_operation_test.payload.PlacedWagonsRequest;
import d3fix4m.ru.train_operation_test.payload.StationDTO;
import d3fix4m.ru.train_operation_test.payload.StationPathDTO;

import java.util.List;

public interface StationService {

    Station getEntityById(Long id);

    StationDTO get(Long id);

    List<StationDTO> getAll();

    StationDTO create(StationDTO dto);

    StationDTO update(Long id, StationDTO dto);

    void delete(Long id);

    StationPathDTO getStationPathById(Long id, Long pathId);

    void addStationPath(Long id);

    void deleteStationPath(Long id, Long pathId);

    void addPlacedWagons(Long id, Long pathId, PlacedWagonsRequest wagons);

    void deletePlacedWagons(Long id, Long pathId, Long count);

    void permutationPlacedWagons(Long id, Long pathId, PlacedWagonsRequest wagons, EChooseSide side);



}
