package d3fix4m.ru.train_operation_test.service.impl;

import d3fix4m.ru.train_operation_test.domain.entity.Cargo;
import d3fix4m.ru.train_operation_test.domain.entity.PlacedWagon;
import d3fix4m.ru.train_operation_test.domain.entity.Wagon;
import d3fix4m.ru.train_operation_test.domain.mapper.CargoMapper;
import d3fix4m.ru.train_operation_test.domain.repository.CargoRepository;
import d3fix4m.ru.train_operation_test.domain.repository.PlacedWagonRepository;
import d3fix4m.ru.train_operation_test.exception.MyError;
import d3fix4m.ru.train_operation_test.exception.MyException;
import d3fix4m.ru.train_operation_test.payload.CargoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CargoServiceImplTest {

    @Mock
    private CargoRepository repository;

    @Mock
    private PlacedWagonRepository placedWagonRepository;

    @Mock
    private CargoMapper mapper;

    @InjectMocks
    private CargoServiceImpl service;

    @Test
    void updateShouldSuccessful() {
        Cargo cargo = mock(Cargo.class);
        CargoDTO dto = new CargoDTO(1L, "test_cargo", 10L);

        when(repository.findById(1L)).thenReturn(Optional.of(cargo));
        when(placedWagonRepository.existsByCargo(cargo)).thenReturn(false);
        when(mapper.toDTO(cargo)).thenReturn(dto);
        when(repository.save(cargo)).thenReturn(cargo);

        CargoDTO result = service.update(1L, dto);

        verify(repository, times(1)).findById(1L);
        verify(placedWagonRepository, times(1)).existsByCargo(cargo);
        verify(mapper, times(1)).toDTO(cargo);
        verify(repository, times(1)).save(cargo);
        assertEquals(dto, result);
    }

    @Test
    void updateShouldThrowExceptionEXCEEDED_WEIGHT() {
        Cargo cargo = mock(Cargo.class);
        PlacedWagon placedWagon = mock(PlacedWagon.class);
        Wagon wagon = mock(Wagon.class);
        CargoDTO dto = new CargoDTO(1L, "test_cargo", 10L);

        when(repository.findById(1L)).thenReturn(Optional.of(cargo));
        when(placedWagonRepository.existsByCargo(cargo)).thenReturn(true);
        when(placedWagonRepository.findAllByCargo(cargo)).thenReturn(List.of(placedWagon));
        when(placedWagon.getWagon()).thenReturn(wagon);
        when(wagon.getCapacity()).thenReturn(1L);

        MyException exception = assertThrows(MyException.class,
                () -> service.update(1L, dto));

        Assertions.assertEquals(MyError.EXCEEDED_WEIGHT, exception.getError());
        verify(repository, times(1)).findById(1L);
        verify(placedWagonRepository, times(1)).existsByCargo(cargo);
        verify(placedWagon, times(1)).getWagon();
        verify(wagon, times(1)).getCapacity();
    }
}