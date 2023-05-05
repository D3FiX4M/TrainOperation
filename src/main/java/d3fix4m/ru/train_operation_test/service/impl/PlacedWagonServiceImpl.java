package d3fix4m.ru.train_operation_test.service.impl;

import d3fix4m.ru.train_operation_test.domain.entity.Cargo;
import d3fix4m.ru.train_operation_test.domain.entity.PlacedWagon;
import d3fix4m.ru.train_operation_test.domain.entity.Wagon;
import d3fix4m.ru.train_operation_test.domain.mapper.CargoMapper;
import d3fix4m.ru.train_operation_test.domain.mapper.PlacedWagonMapper;
import d3fix4m.ru.train_operation_test.domain.mapper.WagonMapper;
import d3fix4m.ru.train_operation_test.domain.repository.PlacedWagonRepository;
import d3fix4m.ru.train_operation_test.exception.MyError;
import d3fix4m.ru.train_operation_test.exception.MyException;
import d3fix4m.ru.train_operation_test.payload.PlacedWagonDTO;
import d3fix4m.ru.train_operation_test.payload.PlacedWagonsFullInfo;
import d3fix4m.ru.train_operation_test.service.CargoService;
import d3fix4m.ru.train_operation_test.service.PlacedWagonService;
import d3fix4m.ru.train_operation_test.service.WagonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class PlacedWagonServiceImpl implements PlacedWagonService {

    private final PlacedWagonRepository repository;
    private final PlacedWagonMapper mapper;
    private final WagonService wagonService;
    private final CargoService cargoService;
    private final WagonMapper wagonMapper;
    private final CargoMapper cargoMapper;

    @Override
    public PlacedWagon getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new MyException(MyError.PLACED_WAGON_NOT_FOUND_WITH_ID)
                );
    }

    @Override
    public PlacedWagonDTO get(Long id) {
        return mapper.toDTO(getEntityById(id));
    }

    @Override
    public List<PlacedWagonDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlacedWagonDTO create(PlacedWagonDTO dto) {

        Wagon wagon = wagonService.getEntityById(dto.getWagonId());
        Cargo cargo = cargoService.getEntityById(dto.getCargoId());

        if (repository.findById(wagon.getId()).isPresent()) {

            log.error("Service - PlacedWagonService," +
                    " Method - create," +
                    " Status: Error");

            throw new MyException(MyError.WAGON_ALREADY_USED);
        }

        if (wagon.getCapacity() < cargo.getWeight()) {

            log.error("Service - PlacedWagonService," +
                    " Method - create," +
                    " Status: Error");

            throw new MyException(MyError.EXCEEDED_WEIGHT);
        }

        PlacedWagon placedWagon = new PlacedWagon();
        placedWagon.setId(dto.getWagonId());
        placedWagon.setWagon(wagon);
        placedWagon.setPosition(0L);
        placedWagon.setCargo(cargo);
        placedWagon.setStationPath(null);
        return mapper.toDTO(repository.save(placedWagon));
    }

    @Override
    public PlacedWagonDTO update(Long id, PlacedWagonDTO dto) {
        PlacedWagon placedWagon = getEntityById(id);
        Cargo cargo = cargoService.getEntityById(dto.getCargoId());
        if (placedWagon.getWagon().getCapacity() < cargo.getWeight()) {

            log.error("Service - PlacedWagonService," +
                    " Method - update," +
                    " Status: Error");

            throw new MyException(MyError.EXCEEDED_WEIGHT);
        }
        placedWagon.setCargo(cargo);
        return mapper.toDTO(repository.save(placedWagon));
    }

    @Override
    public void delete(Long id) {
        repository.delete(getEntityById(id));
    }

    @Override
    public List<PlacedWagonsFullInfo> getFullInfo() {
        List<PlacedWagon> placedWagons = repository.findAll();

        return placedWagons.stream()
                .map(placedWagon -> new PlacedWagonsFullInfo(
                                placedWagon.getPosition(),
                                wagonMapper.toDTO(placedWagon.getWagon()),
                                cargoMapper.toDTO(placedWagon.getCargo())
                        )
                ).collect(Collectors.toList());
    }
}
