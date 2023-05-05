package d3fix4m.ru.train_operation_test.service.impl;

import d3fix4m.ru.train_operation_test.domain.entity.PlacedWagon;
import d3fix4m.ru.train_operation_test.domain.entity.Wagon;
import d3fix4m.ru.train_operation_test.domain.mapper.WagonMapper;
import d3fix4m.ru.train_operation_test.domain.repository.PlacedWagonRepository;
import d3fix4m.ru.train_operation_test.domain.repository.WagonRepository;
import d3fix4m.ru.train_operation_test.exception.MyError;
import d3fix4m.ru.train_operation_test.exception.MyException;
import d3fix4m.ru.train_operation_test.payload.WagonDTO;
import d3fix4m.ru.train_operation_test.service.WagonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class WagonServiceImpl implements WagonService {

    private final WagonRepository repository;
    private final WagonMapper mapper;

    private final PlacedWagonRepository placedWagonRepository;

    @Override
    public Wagon getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new MyException(MyError.WAGON_NOT_FOUND_WITH_ID)
                );
    }

    @Override
    public WagonDTO get(Long id) {
        return mapper.toDTO(getEntityById(id));
    }

    @Override
    public List<WagonDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WagonDTO create(WagonDTO dto) {

        return mapper.toDTO(repository.save(
                        new Wagon(
                                null,
                                dto.getWeight(),
                                dto.getCapacity()
                        )
                )
        );
    }

    @Override
    public WagonDTO update(Long id, WagonDTO dto) {
        Wagon wagon = getEntityById(id);

        Optional<PlacedWagon> placedWagon = placedWagonRepository.findById(id);
        if (placedWagon.isPresent()) {
            if (placedWagon.get().getCargo().getWeight() > dto.getCapacity()) {

                log.error("Service - WagonService," +
                        " Method - update," +
                        " Status: Error");

                throw new MyException(MyError.EXCEEDED_WEIGHT);
            }
        }
        wagon.setWeight(dto.getWeight());
        wagon.setCapacity(dto.getCapacity());
        return mapper.toDTO(repository.save(wagon));
    }

    @Override
    public void delete(Long id) {
        repository.delete(getEntityById(id));
    }

}
