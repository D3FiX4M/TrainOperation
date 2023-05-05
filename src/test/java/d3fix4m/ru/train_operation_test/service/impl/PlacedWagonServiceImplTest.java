package d3fix4m.ru.train_operation_test.service.impl;

import d3fix4m.ru.train_operation_test.domain.entity.Cargo;
import d3fix4m.ru.train_operation_test.domain.entity.PlacedWagon;
import d3fix4m.ru.train_operation_test.domain.entity.Wagon;
import d3fix4m.ru.train_operation_test.domain.mapper.PlacedWagonMapper;
import d3fix4m.ru.train_operation_test.domain.repository.PlacedWagonRepository;
import d3fix4m.ru.train_operation_test.exception.MyError;
import d3fix4m.ru.train_operation_test.exception.MyException;
import d3fix4m.ru.train_operation_test.payload.PlacedWagonDTO;
import d3fix4m.ru.train_operation_test.service.CargoService;
import d3fix4m.ru.train_operation_test.service.WagonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlacedWagonServiceImplTest {
    @Mock
    private PlacedWagonRepository repository;
    @Mock
    private PlacedWagonMapper mapper;
    @Mock
    private WagonService wagonService;
    @Mock
    private CargoService cargoService;

    @InjectMocks
    private PlacedWagonServiceImpl service;

    @Test
    void createShouldSuccessful() {

        Wagon wagon = mock(Wagon.class);
        Cargo cargo = mock(Cargo.class);
        PlacedWagon placedWagon = mock(PlacedWagon.class);
        PlacedWagonDTO dto = new PlacedWagonDTO(1L, 1L, 1L, 1L, 1L);


        when(wagonService.getEntityById(dto.getWagonId())).thenReturn(wagon);
        when(cargoService.getEntityById(dto.getCargoId())).thenReturn(cargo);
        when(repository.findById(wagon.getId())).thenReturn(Optional.empty());
        when(wagon.getCapacity()).thenReturn(10L);
        when(cargo.getWeight()).thenReturn(1L);
        when(mapper.toDTO(placedWagon)).thenReturn(dto);
        when(repository.save(any(PlacedWagon.class))).thenReturn(placedWagon);

        PlacedWagonDTO result = service.create(dto);

        verify(wagonService, times(1)).getEntityById(dto.getWagonId());
        verify(cargoService, times(1)).getEntityById(dto.getCargoId());
        verify(repository, times(1)).findById(wagon.getId());
        verify(wagon, times(1)).getCapacity();
        verify(cargo, times(1)).getWeight();
        verify(mapper, times(1)).toDTO(placedWagon);
        verify(repository, times(1)).save(argThat(pw -> pw.getWagon().equals(wagon)
                && pw.getCargo().equals(cargo)
                && pw.getPosition() == 0L
                && pw.getStationPath() == null));
        assertEquals(dto, result);
    }

    @Test
    void createShouldThrowExceptionWAGON_ALREADY_USED() {

        Wagon wagon = mock(Wagon.class);
        Cargo cargo = mock(Cargo.class);
        PlacedWagonDTO dto = new PlacedWagonDTO(1L, 1L, 1L, 1L, 1L);

        when(wagonService.getEntityById(dto.getWagonId())).thenReturn(wagon);
        when(cargoService.getEntityById(dto.getCargoId())).thenReturn(cargo);
        when(repository.findById(wagon.getId())).thenReturn(Optional.of(new PlacedWagon()));

        MyException exception = assertThrows(MyException.class,
                () -> service.create(dto));

        Assertions.assertEquals(MyError.WAGON_ALREADY_USED, exception.getError());

        verify(wagonService, times(1)).getEntityById(dto.getWagonId());
        verify(cargoService, times(1)).getEntityById(dto.getCargoId());
        verify(repository, times(1)).findById(wagon.getId());
    }

    @Test
    void createShouldThrowExceptionEXCEEDED_WEIGHT() {
        Wagon wagon = mock(Wagon.class);
        Cargo cargo = mock(Cargo.class);
        PlacedWagonDTO dto = new PlacedWagonDTO(1L, 1L, 1L, 1L, 1L);

        when(wagonService.getEntityById(dto.getWagonId())).thenReturn(wagon);
        when(cargoService.getEntityById(dto.getCargoId())).thenReturn(cargo);
        when(repository.findById(wagon.getId())).thenReturn(Optional.empty());
        when(wagon.getCapacity()).thenReturn(1L);
        when(cargo.getWeight()).thenReturn(10L);

        MyException exception = assertThrows(MyException.class,
                () -> service.create(dto));

        Assertions.assertEquals(MyError.EXCEEDED_WEIGHT, exception.getError());

        verify(wagonService, times(1)).getEntityById(dto.getWagonId());
        verify(cargoService, times(1)).getEntityById(dto.getCargoId());
        verify(repository, times(1)).findById(wagon.getId());
        verify(wagon, times(1)).getCapacity();
        verify(cargo, times(1)).getWeight();
    }


    @Test
    void updateShouldSuccessful() {

        PlacedWagon placedWagon = mock(PlacedWagon.class);
        Cargo cargo = mock(Cargo.class);
        Wagon wagon = mock(Wagon.class);
        PlacedWagonDTO dto = new PlacedWagonDTO(1L, 1L, 1L, 1L, 1L);

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(placedWagon));
        when(cargoService.getEntityById(dto.getCargoId())).thenReturn(cargo);
        assert placedWagon != null;
        when(placedWagon.getWagon()).thenReturn(wagon);
        when(placedWagon.getWagon().getCapacity()).thenReturn(10L);
        when(cargo.getWeight()).thenReturn(1L);
        when(mapper.toDTO(placedWagon)).thenReturn(dto);
        when(repository.save(any(PlacedWagon.class))).thenReturn(placedWagon);


        PlacedWagonDTO result = service.update(1L, dto);

        verify(repository, times(1)).findById(1L);
        verify(cargoService, times(1)).getEntityById(dto.getCargoId());
        verify(placedWagon, times(2)).getWagon();
        verify(placedWagon.getWagon(), times(1)).getCapacity();
        verify(cargo, times(1)).getWeight();
        verify(mapper, times(1)).toDTO(placedWagon);
        verify(repository, times(1)).save(placedWagon);
        assertEquals(dto, result);
    }

    @Test
    void updateShouldThrowExceptionEXCEEDED_WEIGHT() {

        PlacedWagon placedWagon = mock(PlacedWagon.class);
        Cargo cargo = mock(Cargo.class);
        Wagon wagon = mock(Wagon.class);
        PlacedWagonDTO dto = new PlacedWagonDTO(1L, 1L, 1L, 1L, 1L);

        when(repository.findById(1L)).thenReturn(Optional.ofNullable(placedWagon));
        when(cargoService.getEntityById(dto.getCargoId())).thenReturn(cargo);
        assert placedWagon != null;
        when(placedWagon.getWagon()).thenReturn(wagon);
        when(placedWagon.getWagon().getCapacity()).thenReturn(1L);
        when(cargo.getWeight()).thenReturn(10L);

        MyException exception = assertThrows(MyException.class,
                () -> service.update(1L, dto));

        Assertions.assertEquals(MyError.EXCEEDED_WEIGHT, exception.getError());
        verify(repository, times(1)).findById(1L);
        verify(cargoService, times(1)).getEntityById(dto.getCargoId());
        verify(placedWagon, times(2)).getWagon();
        verify(placedWagon.getWagon(), times(1)).getCapacity();
        verify(cargo, times(1)).getWeight();
    }

}

