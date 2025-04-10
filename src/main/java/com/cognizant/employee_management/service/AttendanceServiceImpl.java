package com.cognizant.employee_management.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.employee_management.dto.AttendanceDto;
import com.cognizant.employee_management.model.Attendance;
import com.cognizant.employee_management.repository.AttendanceRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.StaleObjectStateException;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    @Override
    @Transactional
    public AttendanceDto createAttendance(@Valid @NotNull AttendanceDto attendanceDto) {
        Attendance attendance = modelMapper.map(attendanceDto, Attendance.class);
        Attendance saved = attendanceRepository.save(attendance);
        return modelMapper.map(saved, AttendanceDto.class);
    }

    @Override
    @Transactional
    public AttendanceDto updateAttendance(int id, @Valid @NotNull AttendanceDto dto) {
        try {
            Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

            attendance.setClockInTime(dto.getClockInTime());
            attendance.setClockOutTime(dto.getClockOutTime());
            attendance.setWorkHours(dto.getWorkHours());
            attendance.setIsPresent(dto.getIsPresent());
            attendance.setEmployee(dto.getEmployee());

            Attendance saved = attendanceRepository.save(attendance);
            return modelMapper.map(saved, AttendanceDto.class);
        } catch (StaleObjectStateException e) {
            throw new RuntimeException("The attendance record was updated or deleted by another transaction", e);
        }
    }

    @Override
    @Transactional
    public AttendanceDto patchAttendance(int id,@NotNull AttendanceDto attendanceDto) {
        try {
            Attendance attendance = attendanceRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Attendance not found"));

            if (attendanceDto.getEmployee() != null) {
                attendance.setEmployee(attendanceDto.getEmployee());
            }
            if (attendanceDto.getClockInTime() != null) {
                attendance.setClockInTime(attendanceDto.getClockInTime());
            }
            if (attendanceDto.getClockOutTime() != null) {
                attendance.setClockOutTime(attendanceDto.getClockOutTime());
            }
            if (attendanceDto.getWorkHours() != 0.0) {
                attendance.setWorkHours(attendanceDto.getWorkHours());
            }

            Attendance updated = attendanceRepository.save(attendance);
            return modelMapper.map(updated, AttendanceDto.class);
        } catch (StaleObjectStateException e) {
            throw new RuntimeException("The attendance record was updated or deleted by another transaction", e);
        }
    }

    @Override
    @Transactional
    public void deleteAttendance(int id) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance not found");
        }
        attendanceRepository.deleteById(id);
    }
}
