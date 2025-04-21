package com.cognizant.employee_management;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.cognizant.employee_management.dto.ShiftDto;
import com.cognizant.employee_management.model.Shift;
import com.cognizant.employee_management.repository.ShiftRepository;
import com.cognizant.employee_management.service.ShiftServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ShiftServiceTest {

    @Mock
    private ShiftRepository shiftRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ShiftServiceImpl shiftService;

    private Shift shift;
    private ShiftDto shiftDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize Shift and ShiftDto
        shift = new Shift();
        shift.setShiftId(1);
        shift.setShiftDate(LocalDate.of(2025, 4, 21));
        shift.setShiftStartTime(LocalTime.of(9, 0));
        shift.setShiftEndTime(LocalTime.of(18, 0));

        shiftDto = new ShiftDto();
        shiftDto.setShiftId(1);
        shiftDto.setShiftDate(LocalDate.of(2025, 4, 21));
        shiftDto.setShiftStartTime(LocalTime.of(9, 0));
        shiftDto.setShiftEndTime(LocalTime.of(18, 0));
    }

    @Test
    void testCreateShift() {
        // Mock behavior
        when(modelMapper.map(shiftDto, Shift.class)).thenReturn(shift);
        when(shiftRepository.save(any(Shift.class))).thenReturn(shift);
        when(modelMapper.map(shift, ShiftDto.class)).thenReturn(shiftDto);

        // Test
        ShiftDto result = shiftService.createShift(shiftDto);

        // Verify and assert
        assertNotNull(result);
        assertEquals(shiftDto.getShiftId(), result.getShiftId());
        verify(shiftRepository, times(1)).save(any(Shift.class));
    }

    @Test
    void testGetShiftById_Success() {
        // Mock behavior
        when(shiftRepository.findById(1)).thenReturn(Optional.of(shift));
        when(modelMapper.map(shift, ShiftDto.class)).thenReturn(shiftDto);

        // Test
        ShiftDto result = shiftService.getShiftById(1);

        // Verify and assert
        assertNotNull(result);
        assertEquals(shiftDto.getShiftId(), result.getShiftId());
        verify(shiftRepository, times(1)).findById(1);
    }

    @Test
    void testGetShiftById_NotFound() {
        // Mock behavior
        when(shiftRepository.findById(99)).thenReturn(Optional.empty());

        // Test
        RuntimeException exception = assertThrows(RuntimeException.class, () -> shiftService.getShiftById(99));

        // Verify and assert
        assertTrue(exception.getMessage().contains("Shift not found"));
        verify(shiftRepository, times(1)).findById(99);
    }

    @Test
    void testGetAllShifts() {
        // Mock behavior
        when(shiftRepository.findAll()).thenReturn(Arrays.asList(shift));
        when(modelMapper.map(any(Shift.class), eq(ShiftDto.class))).thenReturn(shiftDto);

        // Test
        List<ShiftDto> result = shiftService.getAllShifts();

        // Verify and assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(shiftDto.getShiftId(), result.get(0).getShiftId());
        verify(shiftRepository, times(1)).findAll();
    }

    @Test
    void testUpdateShift_Success() {
        // Mock behavior
        when(shiftRepository.findById(1)).thenReturn(Optional.of(shift));
        when(shiftRepository.save(any(Shift.class))).thenReturn(shift);
        when(modelMapper.map(any(Shift.class), eq(ShiftDto.class))).thenReturn(shiftDto);

        // Test
        ShiftDto result = shiftService.updateShift(1, shiftDto);

        // Verify and assert
        assertNotNull(result);
        assertEquals(shiftDto.getShiftId(), result.getShiftId());
        verify(shiftRepository, times(1)).findById(1);
        verify(shiftRepository, times(1)).save(any(Shift.class));
    }

    @Test
    void testUpdateShift_NotFound() {
        // Mock behavior
        when(shiftRepository.findById(99)).thenReturn(Optional.empty());

        // Test
        RuntimeException exception = assertThrows(RuntimeException.class, () -> shiftService.updateShift(99, shiftDto));

        // Verify and assert
        assertTrue(exception.getMessage().contains("Shift not found"));
        verify(shiftRepository, times(1)).findById(99);
    }

    @Test
    void testDeleteShift_Success() {
        // Mock behavior
        when(shiftRepository.existsById(1)).thenReturn(true);
        doNothing().when(shiftRepository).deleteById(1);

        // Test
        assertDoesNotThrow(() -> shiftService.deleteShift(1));

        // Verify and assert
        verify(shiftRepository, times(1)).existsById(1);
        verify(shiftRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteShift_NotFound() {
        // Mock behavior
        when(shiftRepository.existsById(99)).thenReturn(false);

        // Test
        RuntimeException exception = assertThrows(RuntimeException.class, () -> shiftService.deleteShift(99));

        // Verify and assert
        assertTrue(exception.getMessage().contains("Shift not found"));
        verify(shiftRepository, times(1)).existsById(99);
    }
}
