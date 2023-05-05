package d3fix4m.ru.train_operation_test.service.impl;

import d3fix4m.ru.train_operation_test.domain.entity.Cargo;
import d3fix4m.ru.train_operation_test.domain.entity.PlacedWagon;
import d3fix4m.ru.train_operation_test.domain.mapper.CargoMapper;
import d3fix4m.ru.train_operation_test.domain.repository.CargoRepository;
import d3fix4m.ru.train_operation_test.domain.repository.PlacedWagonRepository;
import d3fix4m.ru.train_operation_test.exception.MyError;
import d3fix4m.ru.train_operation_test.exception.MyException;
import d3fix4m.ru.train_operation_test.payload.CargoDTO;
import d3fix4m.ru.train_operation_test.service.CargoService;
import d3fix4m.ru.train_operation_test.service.PlacedWagonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CargoServiceImpl implements CargoService {

    private final CargoRepository repository;
    private final CargoMapper mapper;

    private final PlacedWagonRepository placedWagonRepository;

    @Override
    public Cargo getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new MyException(MyError.CARGO_NOT_FOUND_WITH_ID)
                );
    }

    @Override
    public CargoDTO get(Long id) {
        return mapper.toDTO(getEntityById(id));
    }

    @Override
    public List<CargoDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CargoDTO create(CargoDTO dto) {

        return mapper.toDTO(repository.save(new Cargo(
                                null,
                                dto.getName(),
                                dto.getWeight()
                        )
                )
        );
    }

    @Override
    public CargoDTO update(Long id, CargoDTO dto) {
        Cargo cargo = getEntityById(id);
        if (placedWagonRepository.existsByCargo(cargo)) {
            List<PlacedWagon> placedWagons = placedWagonRepository.findAllByCargo(cargo);
            for (PlacedWagon wagon : placedWagons
            ) {
                if (wagon.getWagon().getCapacity() < dto.getWeight()) {

                    log.error("Service - CargoService," +
                            " Method - update," +
                            " Status: Error");

                    throw new MyException(MyError.EXCEEDED_WEIGHT);
                }
            }
        }
        cargo.setName(dto.getName());
        cargo.setWeight(dto.getWeight());
        return mapper.toDTO(repository.save(cargo));
    }

    @Override
    public void delete(Long id) {
        repository.delete(getEntityById(id));
    }
}
