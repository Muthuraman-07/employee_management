//package com.cognizant.employee_management.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//
//import com.cognizant.employee_management.dto.AttendanceDto;
//import com.cognizant.employee_management.model.Attendance;
//import com.cognizant.employee_management.repository.AttendanceRepository;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.List;
//import java.util.ArrayList;
// 
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
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
// 
//    @BeforeEach
//    void setUp() {
//        attendance = new Attendance();
//        attendance.setId(1L);
//        attendance.setEmployeeId(1001L);
//        attendance.setStatus("Present");
//        attendance.setDate(LocalDateTime.now());
// 
//        attendanceDto = new AttendanceDto();
//        attendanceDto.setId(1L);
//        attendanceDto.setEmployeeId(1001L);
//        attendanceDto.setStatus("Present");
//        attendanceDto.setDate(attendance.getDate());
//    }
// 
//    @Test
//    void testGetAllAttendance() {
//        List<Attendance> attendanceList = new ArrayList<>();
//        attendanceList.add(attendance);
// 
//        when(attendanceRepository.findAll()).thenReturn(attendanceList);
//        when(modelMapper.map(any(Attendance.class), eq(AttendanceDto.class))).thenReturn(attendanceDto);
// 
//        List<AttendanceDto> result = attendanceService.getAllAttendance();
//        assertEquals(1, result.size());
//        assertEquals("Present", result.get(0).getStatus());
//    }
// 
//    @Test
//    void testGetAttendanceById() {
//        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(attendance));
//        when(modelMapper.map(any(Attendance.class), eq(AttendanceDto.class))).thenReturn(attendanceDto);
// 
//        AttendanceDto result = attendanceService.getAttendanceById(1L);
//        assertEquals("Present", result.getStatus());
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
//        assertEquals("Present", result.getStatus());
//    }
// 
//    @Test
//    void testUpdateAttendance() {
//        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(attendance));
//        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
//        when(modelMapper.map(any(Attendance.class), eq(AttendanceDto.class))).thenReturn(attendanceDto);
// 
//        AttendanceDto result = attendanceService.updateAttendance(1L, attendanceDto);
//        assertEquals("Present", result.getStatus());
//    }
// 
//    @Test
//    void testDeleteAttendance() {
//        doNothing().when(attendanceRepository).deleteById(1L);
//        attendanceService.deleteAttendance(1L);
//        verify(attendanceRepository, times(1)).deleteById(1L);
//    }
//}
