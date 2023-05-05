package d3fix4m.ru.train_operation_test.service.impl;

import d3fix4m.ru.train_operation_test.domain.entity.Cargo;
import d3fix4m.ru.train_operation_test.domain.entity.PlacedWagon;
import d3fix4m.ru.train_operation_test.domain.entity.Wagon;
import d3fix4m.ru.train_operation_test.domain.mapper.WagonMapper;
import d3fix4m.ru.train_operation_test.domain.repository.PlacedWagonRepository;
import d3fix4m.ru.train_operation_test.domain.repository.WagonRepository;
import d3fix4m.ru.train_operation_test.exception.MyError;
import d3fix4m.ru.train_operation_test.exception.MyException;
import d3fix4m.ru.train_operation_test.payload.WagonDTO;
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
class WagonServiceImplTest {
    @Mock
    private WagonRepository repository;
    @Mock
    private WagonMapper mapper;
    @Mock
    private PlacedWagonRepository placedWagonRepository;
    @InjectMocks
    WagonServiceImpl service;

    @Test
    void updateShouldThrowExceptionEXCEEDED_WEIGHT() {
        Wagon wagon = mock(Wagon.class);
        Cargo cargo = mock(Cargo.class);
        WagonDTO dto = new WagonDTO(1L, 10L, 1L);
        PlacedWagon placedWagon = mock(PlacedWagon.class);


        when(repository.findById(1L)).thenReturn(Optional.of(wagon));
        when(placedWagonRepository.findById(1L)).thenReturn(Optional.of(placedWagon));
        when(placedWagon.getCargo()).thenReturn(cargo);
        when(placedWagon.getCargo().getWeight()).thenReturn(10L);

        MyException exception = assertThrows(MyException.class,
                () -> service.update(1L, dto));

        Assertions.assertEquals(MyError.EXCEEDED_WEIGHT, exception.getError());
        verify(repository, times(1)).findById(1L);
        verify(placedWagonRepository, times(1)).findById(1L);
        verify(placedWagon, times(2)).getCargo();
        verify(placedWagon.getCargo(), times(1)).getWeight();

    }

    @Test
    void updateShouldSuccessful() {
        Wagon wagon = mock(Wagon.class);
        WagonDTO dto = new WagonDTO(1L, 10L, 50L);


        when(repository.findById(1L)).thenReturn(Optional.of(wagon));
        when(repository.save(wagon)).thenReturn(wagon);
        when(mapper.toDTO(wagon)).thenReturn(dto);

        WagonDTO result = service.update(1L, dto);


        verify(repository, times(1)).findById(1L);
        verify(wagon, times(1)).setWeight(dto.getWeight());
        verify(wagon, times(1)).setCapacity(dto.getCapacity());
        verify(repository, times(1)).save(wagon);
        verify(mapper, times(1)).toDTO(wagon);
        assertEquals(dto, result);
    }
}