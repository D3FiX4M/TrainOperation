package d3fix4m.ru.train_operation_test.service.impl;

import d3fix4m.ru.train_operation_test.domain.entity.EChooseSide;
import d3fix4m.ru.train_operation_test.domain.entity.PlacedWagon;
import d3fix4m.ru.train_operation_test.domain.entity.Station;
import d3fix4m.ru.train_operation_test.domain.entity.StationPath;
import d3fix4m.ru.train_operation_test.domain.repository.StationPathRepository;
import d3fix4m.ru.train_operation_test.domain.repository.StationRepository;
import d3fix4m.ru.train_operation_test.exception.MyError;
import d3fix4m.ru.train_operation_test.exception.MyException;
import d3fix4m.ru.train_operation_test.payload.PlacedWagonsRequest;
import d3fix4m.ru.train_operation_test.service.PlacedWagonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StationServiceImplTest {
    @Mock
    private StationRepository repository;

    @Mock
    private StationPathRepository stationPathRepository;

    @Mock
    private PlacedWagonService placedWagonService;

    @InjectMocks
    private StationServiceImpl service;

    @Test
    void addPlacedWagonsShouldSuccessful() {
        Station station = mock(Station.class);
        StationPath stationPath = mock(StationPath.class);
        PlacedWagonsRequest wagons = new PlacedWagonsRequest(List.of(1L, 2L));
        PlacedWagon placedWagon1 = mock(PlacedWagon.class);
        PlacedWagon placedWagon2 = mock(PlacedWagon.class);

        when(repository.findById(1L)).thenReturn(Optional.of(station));
        when(stationPathRepository.findById(1L)).thenReturn(Optional.of(stationPath));
        when(station.getStationPaths()).thenReturn(List.of(stationPath));
        when(placedWagonService.getEntityById(1L)).thenReturn(placedWagon1);
        when(placedWagonService.getEntityById(2L)).thenReturn(placedWagon2);
        when(placedWagon1.getStationPath()).thenReturn(null);
        when(stationPath.getPlacedWagons()).thenReturn(new ArrayList<>());


        service.addPlacedWagons(1L, 1L, wagons);

        verify(repository, times(1)).findById(1L);
        verify(stationPathRepository, times(1)).findById(1L);
        verify(station, times(1)).getStationPaths();
        verify(placedWagonService, times(1)).getEntityById(1L);
        verify(placedWagonService, times(1)).getEntityById(2L);
        verify(placedWagon1, times(1)).getStationPath();
        verify(stationPath, times(6)).getPlacedWagons();
        verify(placedWagon1, times(1)).setPosition(1L);
        verify(placedWagon1, times(1)).setStationPath(stationPath);
        verify(placedWagon2, times(1)).setPosition(2L);
        verify(placedWagon2, times(1)).setStationPath(stationPath);
        verify(stationPathRepository, times(1)).save(stationPath);

    }

    @Test
    void addPlacedWagonsShouldThrowExceptionWAGON_ALREADY_USED() {

        Station station = mock(Station.class);
        StationPath stationPath = mock(StationPath.class);
        PlacedWagonsRequest wagons = new PlacedWagonsRequest(List.of(1L, 2L));
        PlacedWagon placedWagon1 = mock(PlacedWagon.class);
        PlacedWagon placedWagon2 = mock(PlacedWagon.class);

        when(repository.findById(1L)).thenReturn(Optional.of(station));
        when(stationPathRepository.findById(1L)).thenReturn(Optional.of(stationPath));
        when(station.getStationPaths()).thenReturn(List.of(stationPath));
        when(placedWagonService.getEntityById(1L)).thenReturn(placedWagon1);
        when(placedWagonService.getEntityById(2L)).thenReturn(placedWagon2);
        when(placedWagon1.getStationPath()).thenReturn(stationPath);


        MyException exception = assertThrows(MyException.class,
                () -> service.addPlacedWagons(1L, 1L, wagons));

        assertEquals(MyError.WAGON_ALREADY_USED, exception.getError());

        verify(repository, times(1)).findById(1L);
        verify(stationPathRepository, times(1)).findById(1L);
        verify(station, times(1)).getStationPaths();
        verify(placedWagonService, times(1)).getEntityById(1L);
        verify(placedWagonService, times(1)).getEntityById(2L);
        verify(placedWagon1, times(1)).getStationPath();
    }

    @Test
    void addPlacedWagonsShouldThrowExceptionWAGON_IS_ALREADY_ON_STATION_PATH() {

        Station station = mock(Station.class);
        StationPath stationPath = mock(StationPath.class);
        PlacedWagonsRequest wagons = new PlacedWagonsRequest(List.of(1L, 2L));
        PlacedWagon placedWagon1 = mock(PlacedWagon.class);
        PlacedWagon placedWagon2 = mock(PlacedWagon.class);

        when(repository.findById(1L)).thenReturn(Optional.of(station));
        when(stationPathRepository.findById(1L)).thenReturn(Optional.of(stationPath));
        when(station.getStationPaths()).thenReturn(List.of(stationPath));
        when(placedWagonService.getEntityById(1L)).thenReturn(placedWagon1);
        when(placedWagonService.getEntityById(2L)).thenReturn(placedWagon2);
        when(placedWagon1.getStationPath()).thenReturn(null);
        when(stationPath.getPlacedWagons()).thenReturn(List.of(placedWagon1, placedWagon2));

        MyException exception = assertThrows(MyException.class,
                () -> service.addPlacedWagons(1L, 1L, wagons));

        assertEquals(MyError.WAGON_IS_ALREADY_ON_STATION_PATH, exception.getError());

        verify(repository, times(1)).findById(1L);
        verify(stationPathRepository, times(1)).findById(1L);
        verify(station, times(1)).getStationPaths();
        verify(placedWagonService, times(1)).getEntityById(1L);
        verify(placedWagonService, times(1)).getEntityById(2L);
        verify(placedWagon1, times(1)).getStationPath();
        verify(stationPath, times(1)).getPlacedWagons();

    }



    @Test
    void deletePlacedWagonsShouldThrowExceptionNOT_ENOUGH_WAGONS() {
        Station station = mock(Station.class);
        StationPath stationPath = mock(StationPath.class);
        PlacedWagon placedWagon1 = mock(PlacedWagon.class);
        PlacedWagon placedWagon2 = mock(PlacedWagon.class);

        when(repository.findById(1L)).thenReturn(Optional.of(station));
        when(stationPathRepository.findById(1L)).thenReturn(Optional.of(stationPath));
        when(station.getStationPaths()).thenReturn(List.of(stationPath));
        when(stationPath.getPlacedWagons()).thenReturn(List.of(placedWagon1, placedWagon2));


        MyException exception = assertThrows(MyException.class,
                () -> service.deletePlacedWagons(1L, 1L, 3L));

        assertEquals(MyError.NOT_ENOUGH_WAGONS, exception.getError());

        verify(repository, times(1)).findById(1L);
        verify(stationPathRepository, times(1)).findById(1L);
        verify(station, times(1)).getStationPaths();
        verify(stationPath, times(1)).getPlacedWagons();
    }


    @Test
    void permutationPlacedWagonsShouldThrowExceptionWAGON_IS_NOT_AT_THE_STATION() {
        Station station = new Station();
        StationPath stationPath = new StationPath();
        PlacedWagon placedWagon1 = new PlacedWagon();
        PlacedWagon placedWagon2 = new PlacedWagon();
        PlacedWagon placedWagon3 = new PlacedWagon();

        station.setId(1L);
        station.setStationPaths(List.of(stationPath));
        stationPath.setId(2L);
        placedWagon1.setId(3L);
        placedWagon2.setId(4L);
        placedWagon3.setId(5L);
        placedWagon1.setPosition(1L);
        placedWagon2.setPosition(2L);
        placedWagon3.setPosition(3L);
        placedWagon1.setStationPath(new StationPath());
        placedWagon2.setStationPath(new StationPath());
        placedWagon3.setStationPath(new StationPath());
        stationPath.setPlacedWagons(List.of(placedWagon1, placedWagon2, placedWagon3));

        when(repository.findById(1L)).thenReturn(Optional.of(station));
        when(stationPathRepository.findById(2L)).thenReturn(Optional.of(stationPath));
        when(placedWagonService.getEntityById(3L)).thenReturn(placedWagon1);
        when(placedWagonService.getEntityById(4L)).thenReturn(placedWagon2);
        when(placedWagonService.getEntityById(5L)).thenReturn(placedWagon3);

        PlacedWagonsRequest wagons = new PlacedWagonsRequest();
        wagons.setWagonIds(List.of(3L, 4L, 5L));

        MyException exception = assertThrows(MyException.class, () -> {
            service.permutationPlacedWagons(1L, 2L, wagons, EChooseSide.START);
        });

        assertEquals(MyError.WAGON_IS_NOT_AT_THE_STATION, exception.getError());

        verify(repository, times(1)).findById(1L);
        verify(stationPathRepository, times(1)).findById(2L);
        verify(placedWagonService, times(1)).getEntityById(3L);
        verify(placedWagonService, times(1)).getEntityById(4L);
        verify(placedWagonService, times(1)).getEntityById(5L);
        verify(stationPathRepository, never()).save(stationPath);
    }

    @Test
    void permutationPlacedWagonsShouldThrowExceptionWAGON_IS_ALREADY_ON_STATION_PATH() {

        Station station = new Station();
        StationPath stationPath = new StationPath();
        PlacedWagon placedWagon1 = new PlacedWagon();
        PlacedWagon placedWagon2 = new PlacedWagon();
        PlacedWagon placedWagon3 = new PlacedWagon();

        station.setId(1L);
        station.setStationPaths(List.of(stationPath));
        stationPath.setId(2L);
        placedWagon1.setId(3L);
        placedWagon2.setId(4L);
        placedWagon3.setId(5L);
        placedWagon1.setPosition(1L);
        placedWagon2.setPosition(2L);
        placedWagon3.setPosition(3L);
        placedWagon1.setStationPath(stationPath);
        placedWagon2.setStationPath(stationPath);
        placedWagon3.setStationPath(stationPath);
        stationPath.setPlacedWagons(List.of(placedWagon1, placedWagon2, placedWagon3));

        PlacedWagonsRequest wagons = new PlacedWagonsRequest();
        wagons.setWagonIds(List.of(3L, 4L, 5L));

        when(repository.findById(1L)).thenReturn(Optional.of(station));
        when(stationPathRepository.findById(2L)).thenReturn(Optional.of(stationPath));
        when(placedWagonService.getEntityById(3L)).thenReturn(placedWagon1);
        when(placedWagonService.getEntityById(4L)).thenReturn(placedWagon2);
        when(placedWagonService.getEntityById(5L)).thenReturn(placedWagon3);


        MyException exception = assertThrows(MyException.class, () -> {
            service.permutationPlacedWagons(1L, 2L, wagons, EChooseSide.START);
        });


        assertEquals(MyError.WAGON_IS_ALREADY_ON_STATION_PATH, exception.getError());

        verify(repository, times(1)).findById(1L);
        verify(stationPathRepository, times(1)).findById(2L);
        verify(placedWagonService, times(1)).getEntityById(3L);
        verify(placedWagonService, times(1)).getEntityById(4L);
        verify(placedWagonService, times(1)).getEntityById(5L);
        verify(stationPathRepository, never()).save(stationPath);
    }
}