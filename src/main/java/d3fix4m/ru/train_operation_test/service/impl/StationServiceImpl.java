package d3fix4m.ru.train_operation_test.service.impl;

import d3fix4m.ru.train_operation_test.domain.entity.EChooseSide;
import d3fix4m.ru.train_operation_test.domain.entity.PlacedWagon;
import d3fix4m.ru.train_operation_test.domain.entity.Station;
import d3fix4m.ru.train_operation_test.domain.entity.StationPath;
import d3fix4m.ru.train_operation_test.domain.mapper.StationMapper;
import d3fix4m.ru.train_operation_test.domain.mapper.StationPathMapper;
import d3fix4m.ru.train_operation_test.domain.repository.StationPathRepository;
import d3fix4m.ru.train_operation_test.domain.repository.StationRepository;
import d3fix4m.ru.train_operation_test.exception.MyError;
import d3fix4m.ru.train_operation_test.exception.MyException;
import d3fix4m.ru.train_operation_test.payload.PlacedWagonsRequest;
import d3fix4m.ru.train_operation_test.payload.StationDTO;
import d3fix4m.ru.train_operation_test.payload.StationPathDTO;
import d3fix4m.ru.train_operation_test.service.PlacedWagonService;
import d3fix4m.ru.train_operation_test.service.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository repository;

    private final StationMapper mapper;
    private final StationPathRepository stationPathRepository;
    private final StationPathMapper stationPathMapper;
    private final PlacedWagonService placedWagonService;

    @Override
    public Station getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new MyException(MyError.STATION_NOT_FOUND_WITH_ID)
                );
    }

    @Override
    public StationDTO get(Long id) {
        return mapper.toDTO(getEntityById(id));
    }

    @Override
    public List<StationDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StationDTO create(StationDTO dto) {
        return mapper.toDTO(repository.save(new Station(
                                null,
                                dto.getName(),
                                new ArrayList<>()
                        )
                )
        );
    }

    @Override
    public StationDTO update(Long id, StationDTO dto) {
        Station station = getEntityById(id);
        station.setName(dto.getName());
        return mapper.toDTO(repository.save(station));
    }

    @Override
    public void delete(Long id) {
        repository.delete(getEntityById(id));
    }


    StationPath getStationPathEntityById(Long id) {
        return stationPathRepository.findById(id)
                .orElseThrow(
                        () -> new MyException(MyError.STATION_PATH_NOT_FOUND_WITH_ID)
                );
    }

    @Override
    public StationPathDTO getStationPathById(Long id, Long pathId) {
        return stationPathMapper.toDTO(getStationPathEntityById(pathId));
    }

    @Override
    public void addStationPath(Long id) {
        Station station = getEntityById(id);
        station.getStationPaths().add(new StationPath(
                        null,
                        station,
                        new ArrayList<>()
                )
        );

        repository.save(station);

        log.info("Service - StationService," +
                " Method - addStationPath," +
                " Status: Complete");

    }

    void checkStationPathOnStation(Station station, StationPath stationPath) {
        if (!station.getStationPaths().contains(stationPath)) {

            log.info("Service - StationService," +
                    " Method - checkStationPathOnStation," +
                    " Status: Error");

            throw new MyException(MyError.PATH_DOES_NOT_BELONG_TO_THE_STATION);
        }
    }

    @Override
    public void deleteStationPath(Long id,
                                  Long pathId) {
        Station station = getEntityById(id);
        StationPath stationPath = getStationPathEntityById(pathId);

        checkStationPathOnStation(station, stationPath);

        station.getStationPaths().remove(stationPath);
        stationPathRepository.delete(stationPath);

        log.info("Service - StationService," +
                " Method - deleteStationPath," +
                " Status: Complete");
    }


    @Override
    @Transactional
    public void addPlacedWagons(Long id,
                                Long pathId,
                                PlacedWagonsRequest wagons) {

        Station station = getEntityById(id);
        StationPath stationPath = getStationPathEntityById(pathId);
        checkStationPathOnStation(station, stationPath);

        List<PlacedWagon> placedWagons = wagons.getWagonIds()
                .stream()
                .map(placedWagonService::getEntityById).toList();

        for (PlacedWagon wagon : placedWagons
        ) {
            if (wagon.getStationPath() != null) {

                log.info("Service - StationService," +
                        " Method - addPlacedWagons," +
                        " Status: Error");

                throw new MyException(MyError.WAGON_ALREADY_USED);
            }
            if (stationPath.getPlacedWagons().contains(wagon)) {

                log.info("Service - StationService," +
                        " Method - addPlacedWagons," +
                        " Status: Error");

                throw new MyException(MyError.WAGON_IS_ALREADY_ON_STATION_PATH);
            }
            wagon.setPosition((long) stationPath.getPlacedWagons().size() + 1);
            wagon.setStationPath(stationPath);
            stationPath.getPlacedWagons().add(wagon);
        }
        stationPathRepository.save(stationPath);

        log.info("Service - StationService," +
                " Method - addPlacedWagons," +
                " Status: Complete");
    }

    @Override
    @Transactional
    public void deletePlacedWagons(Long id,
                                   Long pathId,
                                   Long count) {

        Station station = getEntityById(id);
        StationPath stationPath = getStationPathEntityById(pathId);
        checkStationPathOnStation(station, stationPath);

        if (count > stationPath.getPlacedWagons().size()) {

            log.info("Service - StationService," +
                    " Method - deletePlacedWagons," +
                    " Status: Error");

            throw new MyException(MyError.NOT_ENOUGH_WAGONS);
        }

        for (int i = 0; i < count; i++) {
            PlacedWagon placedWagon = stationPath.getPlacedWagons().get(0);
            placedWagon.setPosition(0L);
            placedWagon.setStationPath(null);
            stationPath.getPlacedWagons().remove(0);
        }
        for (PlacedWagon wagon : stationPath.getPlacedWagons()
        ) {
            wagon.setPosition(wagon.getPosition() - count);
        }
        stationPathRepository.save(stationPath);

        log.info("Service - StationService," +
                " Method - deletePlacedWagons," +
                " Status: Complete");
    }

    @Override
    @Transactional
    public void permutationPlacedWagons(Long id,
                                        Long pathId,
                                        PlacedWagonsRequest wagons,
                                        EChooseSide side) {

        Station station = getEntityById(id);
        StationPath stationPath = getStationPathEntityById(pathId);
        checkStationPathOnStation(station, stationPath);

        List<PlacedWagon> placedWagons = wagons.getWagonIds()
                .stream()
                .map(placedWagonService::getEntityById).toList();

        for (PlacedWagon wagon : placedWagons) {
            if (!station.getStationPaths().contains(wagon.getStationPath())) {

                log.info("Service - StationService," +
                        " Method - permutationPlacedWagons," +
                        " Status: Error");

                throw new MyException(MyError.WAGON_IS_NOT_AT_THE_STATION);
            }
            if (stationPath.getPlacedWagons().contains(wagon)) {

                log.info("Service - StationService," +
                        " Method - permutationPlacedWagons," +
                        " Status: Error");

                throw new MyException(MyError.WAGON_IS_ALREADY_ON_STATION_PATH);
            }
            switch (side) {
                case START -> {
                    stationPath.getPlacedWagons().forEach(
                            placedWagon -> placedWagon.setPosition(
                                    placedWagon.getPosition() + 1)
                    );
                    wagon.setPosition(1L);
                    wagon.setStationPath(stationPath);
                    stationPath.getPlacedWagons().add(0, wagon);
                }
                case END -> {
                    wagon.setPosition((long) stationPath.getPlacedWagons().size() + 1);
                    wagon.setStationPath(stationPath);
                    stationPath.getPlacedWagons().add(wagon);
                }
            }
            stationPathRepository.save(stationPath);

            log.info("Service - StationService," +
                    " Method - permutationPlacedWagons," +
                    " Status: Complete");
        }
    }
}
