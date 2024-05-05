package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.controller.dto.DepartmentIncomingDto;
import spring.controller.dto.DepartmentOutGoingDto;
import spring.controller.dto.DepartmentWithProfessorsOutGoingDto;
import spring.controller.dto.ExceptionDto;
import spring.exception.NotFoundException;
import spring.exception.RepositoryException;
import spring.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}")
    public DepartmentWithProfessorsOutGoingDto getDepartmentById(@PathVariable("id") int id) {
        return departmentService.findById(id);
    }

    @GetMapping
    public List<DepartmentOutGoingDto> getDepartments() {
        return departmentService.findAll();
    }

    @PostMapping
    public DepartmentOutGoingDto createDepartment(@RequestBody DepartmentIncomingDto departmentIncomingDto) {
        return  departmentService.save(departmentIncomingDto);
    }

    @PutMapping("/{id}")
    public void updateDepartment(@PathVariable("id") int id, @RequestBody DepartmentIncomingDto departmentIncomingDto) {
        departmentIncomingDto.setId(id);
        departmentService.update(departmentIncomingDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable("id") int id) {
        departmentService.deleteById(id);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ExceptionDto> catchNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RepositoryException.class})
    public ResponseEntity<ExceptionDto> catchRepositoryException(RepositoryException e) {
        return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
