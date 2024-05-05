package spring.service;

import spring.controller.dto.DepartmentIncomingDto;
import spring.controller.dto.DepartmentOutGoingDto;
import spring.controller.dto.DepartmentWithProfessorsOutGoingDto;

import java.util.List;

public interface DepartmentService {
    DepartmentOutGoingDto save(DepartmentIncomingDto departmentIncomingDto);
    void update(DepartmentIncomingDto departmentIncomingDto);
    DepartmentWithProfessorsOutGoingDto findById(Integer id);
    List<DepartmentOutGoingDto> findAll();
    void deleteById(Integer id);
}
