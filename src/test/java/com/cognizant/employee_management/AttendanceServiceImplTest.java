//package com.cognizant.employee_management;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//
//import com.cognizant.employee_management.dto.AttendanceDto;
//import com.cognizant.employee_management.model.Attendance;
//import com.cognizant.employee_management.model.Employee;
//import com.cognizant.employee_management.repository.AttendanceRepository;
//import com.cognizant.employee_management.service.AttendanceServiceImpl;
//
//@ExtendWith(MockitoExtension.class)
//public class AttendanceServiceImplTest {
//
//    @Mock
//    private AttendanceRepository attendanceRepository;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @InjectMocks
//    private AttendanceServiceImpl attendanceService;
//
//    private Attendance attendance;
//    private AttendanceDto attendanceDto;
//    private Employee employee;
//
//    {
//        employee = new Employee();
//        employee.setEmployeeId(1);
//
//        attendance = new Attendance();
//        attendance.setAttendanceID(1);
//        attendance.setEmployee(employee);
//        attendance.setClockInTime(LocalDateTime.now());
//        attendance.setClockOutTime(LocalDateTime.now().plusHours(8));
//        attendance.setWorkHours(8.0f);
//        attendance.setIsPresent(1);
//
//        attendanceDto = new AttendanceDto();
//        attendanceDto.setAttendanceID(1);
//        attendanceDto.setEmployee(employee);
//        attendanceDto.setClockInTime(LocalDateTime.now());
//        attendanceDto.setClockOutTime(LocalDateTime.now().plusHours(8));
//        attendanceDto.setWorkHours(8.0f);
//        attendanceDto.setIsPresent(1);
//    }
//
//    @Test
//    void testGetAllAttendance() {
//        when(attendanceRepository.findAll()).thenReturn(Arrays.asList(attendance));
//
//        assertNotNull(attendanceService.getAllAttendance());
//        verify(attendanceRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testCreateAttendance() {
//        when(modelMapper.map(any(AttendanceDto.class), eq(Attendance.class))).thenReturn(attendance);
//        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
//        when(modelMapper.map(any(Attendance.class), eq(AttendanceDto.class))).thenReturn(attendanceDto);
//
//        AttendanceDto result = attendanceService.createAttendance(attendanceDto);
//        assertNotNull(result);
//        verify(attendanceRepository, times(1)).save(any(Attendance.class));
//    }
//
//    @Test
//    void testUpdateAttendance() {
//        when(attendanceRepository.findById(1)).thenReturn(Optional.of(attendance));
//        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
//        when(modelMapper.map(any(Attendance.class), eq(AttendanceDto.class))).thenReturn(attendanceDto);
//
//        AttendanceDto result = attendanceService.updateAttendance(1, attendanceDto);
//        assertNotNull(result);
//        verify(attendanceRepository, times(1)).findById(1);
//        verify(attendanceRepository, times(1)).save(any(Attendance.class));
//    }
//
//    @Test
//    void testPatchAttendance() {
//        when(attendanceRepository.findById(1)).thenReturn(Optional.of(attendance));
//        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
//        when(modelMapper.map(any(Attendance.class), eq(AttendanceDto.class))).thenReturn(attendanceDto);
//
//        AttendanceDto result = attendanceService.patchAttendance(1, attendanceDto);
//        assertNotNull(result);
//        verify(attendanceRepository, times(1)).findById(1);
//        verify(attendanceRepository, times(1)).save(any(Attendance.class));
//    }
//
//    @Test
//    void testDeleteAttendance() {
//        when(attendanceRepository.existsById(1)).thenReturn(true);
//
//        attendanceService.deleteAttendance(1);
//        verify(attendanceRepository, times(1)).existsById(1);
//        verify(attendanceRepository, times(1)).deleteById(1);
//    }
//
//    @Test
//    void testDeleteAttendance_NotFound() {
//        when(attendanceRepository.existsById(99)).thenReturn(false);
//
//        Exception exception = assertThrows(RuntimeException.class, () ->
//                attendanceService.deleteAttendance(99)
//        );
//
//        assertTrue(exception.getMessage().contains("Attendance not found"));
//    }
//}
