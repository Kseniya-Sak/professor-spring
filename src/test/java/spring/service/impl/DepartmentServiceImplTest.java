package spring.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spring.controller.dto.DepartmentIncomingDto;
import spring.controller.dto.DepartmentOutGoingDto;
import spring.controller.dto.DepartmentWithProfessorsOutGoingDto;
import spring.controller.mapper.DepartmentDtoMapper;
import spring.entity.Department;
import spring.entity.Professor;
import spring.exception.NotFoundException;
import spring.exception.RepositoryException;
import spring.repository.DepartmentRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DepartmentServiceImplTest {
    private static DepartmentRepository mockRepository;
    private static DepartmentDtoMapper mockMapper;
    private static DepartmentServiceImpl departmentService;

    @BeforeEach
    void setDepartmentService() {
        mockRepository = Mockito.mock(DepartmentRepository.class);
        mockMapper = Mockito.mock(DepartmentDtoMapper.class);
        departmentService = new DepartmentServiceImpl(mockRepository, mockMapper);
    }

    @Test
    @DisplayName("Test departmentService.findAll")
    void findAll_should_getDepartments() {
        List<Department> departments = List.of(new Department(1, "testName"));
        when(mockRepository.findAll()).thenReturn(departments);
        when(mockMapper
                .mapToDepartmentOutGoingDto(departments.get(0)))
                .thenReturn(new DepartmentOutGoingDto(1, "testName"));

        List<DepartmentOutGoingDto> actual = departmentService.findAll();
        assertAll(
                () -> assertEquals(1, actual.size()),
                () -> assertEquals(1, actual.get(0).getId())
        );
    }

    @Test
    @DisplayName("Test departmentService.findById() exists Department with such Id")
    void findById_should_getDepartment() {
        Department department = new Department();
        department.setId(1);
        department.setName("test");
        department.setProfessors(List.of(new Professor(1, "testN", "testS")));
        when(mockRepository.findByIdWithProfessor(1))
                .thenReturn(Optional.of(department));
        when(mockMapper.mapToDepartmentWithProfessorsOutGoingDto(department))
                .thenReturn(new DepartmentWithProfessorsOutGoingDto(department.getId(), department.getName(), null));
        DepartmentWithProfessorsOutGoingDto actual = departmentService.findById(1);

        assertAll(
                () -> assertTrue(Objects.nonNull(actual)),
                () -> assertEquals(1, actual.getId()),
                () -> assertEquals("test", actual.getName())
        );
    }

    @Test
    @DisplayName("Test departmentService.findById() throws exception")
    void findById_should_throwException_when_nullParameter() {
        assertThrows(RepositoryException.class,
                () -> departmentService.findById(null));

    }

    @Test
    @DisplayName("Test departmentService.findById() doesn't exist Department with such Id")
    void findById_should_throwException_when_departmentDoesNotEexist() {
        when(mockRepository.findByIdWithProfessor(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> departmentService.findById(1));

    }

    @Test
    @DisplayName("Test departmentService.save() successful")
    void save_should_saveDepartment() {
        Department getDepartment = new Department("test");
        Department savedDepartment = new Department(1, "test");
        DepartmentIncomingDto departmentIncomingDto = new DepartmentIncomingDto(null, "test");
        DepartmentOutGoingDto departmentOutGoingDto = new DepartmentOutGoingDto(1, "test");

        when(mockRepository.existsByName("test")).thenReturn(false);
        when(mockRepository.save(getDepartment))
                .thenReturn(savedDepartment);

        when(mockMapper.mapToDepartment(departmentIncomingDto))
                .thenReturn(getDepartment);
        when(mockMapper.mapToDepartmentOutGoingDto(savedDepartment))
                .thenReturn(departmentOutGoingDto);

        DepartmentOutGoingDto actual = departmentService.save(departmentIncomingDto);
        assertAll(
                () -> assertTrue(Objects.nonNull(actual)),
                () -> assertEquals(1, actual.getId())
        );
    }

    @Test
    @DisplayName("Test departmentService.save() throws exception")
    void save_should_throwException_when_nullParameter() {
        DepartmentIncomingDto departmentIncomingDto = new DepartmentIncomingDto(null, null);
        assertAll(
                () -> assertThrows(RepositoryException.class,
                        () -> departmentService.save(null)),
                () -> assertThrows(RepositoryException.class,
                        () -> departmentService.save(departmentIncomingDto))
        );
    }

    @Test
    @DisplayName("Test departmentService.save() department's name already exists")
    void save_should_throwException_when_nameAlreadyExist() {
        when(mockRepository.existsByName("test")).thenReturn(true);

        DepartmentIncomingDto departmentIncomingDto = new DepartmentIncomingDto(null, "test");
        assertThrows(RepositoryException.class,
                () -> departmentService.save(departmentIncomingDto));
    }

    @Test
    @DisplayName("Test departmentService.update() successful")
    void update_should_updateDepartment() {
        Department department = new Department(1, "test");
        DepartmentIncomingDto departmentIncomingDto = new DepartmentIncomingDto(1, "test");

        when(mockRepository.existsById(1))
                .thenReturn(true);
        when(mockRepository.save(department))
                .thenReturn(department);

        when(mockMapper.mapToDepartment(departmentIncomingDto))
                .thenReturn(department);
        departmentService.update(departmentIncomingDto);

        verify(mockRepository, times(1)).save(department);
    }

    @Test
    @DisplayName("Test departmentService.update() throws exception")
    void update_should_throwException_when_departmentWithSuchIdDoesNotExist() {
        DepartmentIncomingDto departmentIncomingDto = new DepartmentIncomingDto(1, "test");

        when(mockRepository.existsById(1))
                .thenReturn(false);

        assertThrows(NotFoundException.class, () -> departmentService.update(departmentIncomingDto));
    }

    @Test
    @DisplayName("Test departmentService.update() throws exception")
    void update_should_throwException_when_nullParameter() {
        DepartmentIncomingDto departmentIncomingDto = new DepartmentIncomingDto(1, null);
        assertAll(
                () -> assertThrows(RepositoryException.class,
                        () -> departmentService.update(null)),
                () -> assertThrows(RepositoryException.class,
                        () -> departmentService.update(departmentIncomingDto))
        );
    }


    @Test
    @DisplayName("Test departmentService.deleteById successful")
    void deleteById_should_deleteDepartment() {
        Department department = new Department(1, "test");
        when(mockRepository.existsById(1))
                .thenReturn(true);
        when(mockMapper.mapToDepartmentWithProfessorsOutGoingDto(department))
                .thenReturn(new DepartmentWithProfessorsOutGoingDto(1, "test", null));

        departmentService.deleteById(1);

        Mockito.verify(mockRepository,times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Test departmentService.deleteById throws Exception when id==null")
    void deleteById_should_throwException_when_nullParameter() {
        assertThrows(RepositoryException.class,
                () -> departmentService.deleteById(null));

    }

    @Test
    @DisplayName("Test departmentService.deleteById when department with such id doesn't exitst")
    void deleteById_should_throwException_when_departmentDoesNotExist() {
        when(mockRepository.findByIdWithProfessor(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> departmentService.deleteById(1));

    }
}