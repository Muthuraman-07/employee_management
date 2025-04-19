//package com.cognizant.employee_management;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//
//import com.cognizant.employee_management.dto.ShiftDto;
//import com.cognizant.employee_management.model.Shift;
//import com.cognizant.employee_management.repository.ShiftRepository;
//import com.cognizant.employee_management.service.ShiftServiceImpl;
//@ExtendWith(MockitoExtension.class)
//public class ShiftServiceTest {
//
//    @Mock
//    private ShiftRepository shiftRepository;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @InjectMocks
//    private ShiftServiceImpl shiftService;
//
//    private Shift shift;
//    private ShiftDto shiftDto;
//
//    @BeforeEach
//    void setUp() {
//        shift = new Shift();
//        shift.setShiftId(1);
//        shift.setShiftDate(LocalDate.of(2025, 4, 16));
//        shift.setShiftStartTime(LocalTime.of(9, 0));
//        shift.setShiftEndTime(LocalTime.of(17, 0));
//
//        shiftDto = new ShiftDto();
//        shiftDto.setShiftId(1);
//        shiftDto.setShiftDate(LocalDate.of(2025, 4, 16));
//        shiftDto.setShiftStartTime(LocalTime.of(9, 0));
//        shiftDto.setShiftEndTime(LocalTime.of(17, 0));
//    }
//
//    @Test
//    void testCreateShift() {
//        when(modelMapper.map(shiftDto, Shift.class)).thenReturn(shift);
//        when(shiftRepository.save(shift)).thenReturn(shift);
//        when(modelMapper.map(shift, ShiftDto.class)).thenReturn(shiftDto);
//
//        ShiftDto result = shiftService.createShift(shiftDto);
//
//        assertEquals(shiftDto, result);
//        verify(shiftRepository, times(1)).save(shift);
//    }
//
//    @Test
//    void testGetShiftById() {
//        when(shiftRepository.findById(1)).thenReturn(Optional.of(shift));
//        when(modelMapper.map(shift, ShiftDto.class)).thenReturn(shiftDto);
//
//        ShiftDto result = shiftService.getShiftById(1);
//
//        assertEquals(shiftDto, result);
//        verify(shiftRepository, times(1)).findById(1);
//    }
//
//    @Test
//    void testGetAllShifts() {
//        when(shiftRepository.findAll()).thenReturn(Arrays.asList(shift));
//        when(modelMapper.map(shift, ShiftDto.class)).thenReturn(shiftDto);
//
//        List<ShiftDto> result = shiftService.getAllShifts();
//
//        assertEquals(1, result.size());
//        assertEquals(shiftDto, result.get(0));
//        verify(shiftRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testUpdateShift() {
//        when(shiftRepository.findById(1)).thenReturn(Optional.of(shift));
//        when(shiftRepository.save(shift)).thenReturn(shift);
//        when(modelMapper.map(shift, ShiftDto.class)).thenReturn(shiftDto);
//
//        ShiftDto result = shiftService.updateShift(1, shiftDto);
//
//        assertEquals(shiftDto, result);
//        verify(shiftRepository, times(1)).findById(1);
//        verify(shiftRepository, times(1)).save(shift);
//    }
//
////    @Test
////    void testPatchShift() {
////        when(shiftRepository.findById(1)).thenReturn(Optional.of(shift));
////        when(shiftRepository.save(shift)).thenReturn(shift);
////        when(modelMapper.map(shift, ShiftDto.class)).thenReturn(shiftDto);
////
////        ShiftDto patchDto = new ShiftDto();
////        patchDto.setShiftStartTime(LocalTime.of(10, 0));
////
////        ShiftDto result = shiftService.patchShift(1, patchDto);
////
////        assertEquals(shiftDto.getShiftDate(), result.getShiftDate());
////        assertEquals(LocalTime.of(10, 0), result.getShiftStartTime());
////        assertEquals(shiftDto.getShiftEndTime(), result.getShiftEndTime());
////        verify(shiftRepository, times(1)).findById(1);
////        verify(shiftRepository, times(1)).save(shift);
////    }
//
////    @Test
////    void testDeleteShift() {
////        when(shiftRepository.existsById(1)).thenReturn(true);
////
////        shiftService.deleteShift(1);
////
////        verify(shiftRepository, times(1)).existsById(1);
////        verify(shiftRepository, times(1)).deleteById(1);
////    }
//    @Test
//    void testDeleteShift() {
//        // Mock the repository to return true for existsById
//        when(shiftRepository.existsById(1)).thenReturn(true);
//
//        // Call the service method
//        shiftService.deleteShift(1);
//
//        // Verify the interactions
//        verify(shiftRepository, times(1)).existsById(1);
//        verify(shiftRepository, times(1)).deleteById(1);
//    }
//
//}
//
//
