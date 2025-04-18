package com.cognizant.employee_management.service;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cognizant.employee_management.dto.ShiftDto;
import com.cognizant.employee_management.model.Shift;
import com.cognizant.employee_management.repository.ShiftRepository;
@Service
public class ShiftServiceImpl implements ShiftService {
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ShiftDto createShift(ShiftDto shiftDto) {
        Shift shift = modelMapper.map(shiftDto, Shift.class);
        Shift saved = shiftRepository.save(shift);
        return modelMapper.map(saved, ShiftDto.class);
    }
    @Override
    public ShiftDto getShiftById(int id) {
        Shift shift = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        return modelMapper.map(shift, ShiftDto.class);
    }
    @Override
    public List<ShiftDto> getAllShifts() {
        return shiftRepository.findAll().stream()
                .map(shift -> modelMapper.map(shift, ShiftDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public ShiftDto updateShift(int id, ShiftDto shiftDto) {
        Shift existing = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        existing.setShiftDate(shiftDto.getShiftDate());
        existing.setShiftStartTime(shiftDto.getShiftStartTime());
        existing.setShiftEndTime(shiftDto.getShiftEndTime());
        Shift updated = shiftRepository.save(existing);
        return modelMapper.map(updated, ShiftDto.class);
    }
    @Override
    public ShiftDto patchShift(int id, ShiftDto shiftDto) {
        Shift existing = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        if (shiftDto.getShiftDate() != null) {
            existing.setShiftDate(shiftDto.getShiftDate());
        }
        if (shiftDto.getShiftStartTime() != null) {
            existing.setShiftStartTime(shiftDto.getShiftStartTime());
        }
        if (shiftDto.getShiftEndTime() != null) {
            existing.setShiftEndTime(shiftDto.getShiftEndTime());
        }
        Shift patched = shiftRepository.save(existing);
        return modelMapper.map(patched, ShiftDto.class);
    }
//    @Override
//    public void deleteShift(int id) {
//        shiftRepository.deleteById(id);
//    }
    @Override
    public void deleteShift(int id) {
        if (!shiftRepository.existsById(id)) {
            throw new RuntimeException("Shift not found with id: " + id);
        }
        System.out.println("Shift exists, proceeding to delete."); // Debug statement
        shiftRepository.deleteById(id);
    }

}
 