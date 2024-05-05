package spring.controller.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spring.controller.dto.DepartmentIncomingDto;
import spring.controller.dto.DepartmentOutGoingDto;
import spring.controller.dto.DepartmentWithProfessorsOutGoingDto;
import spring.controller.dto.ProfessorShortOutGoingDto;
import spring.entity.Department;
import spring.entity.Professor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class DepartmentDtoMapperTest {

    private static DepartmentDtoMapper departmentDtoMapper;
    private static ProfessorDtoMapper mockProfessorDtoMapper;

    @BeforeEach
    void setUp() {
        mockProfessorDtoMapper = Mockito.mock(ProfessorDtoMapperImpl.class);
        departmentDtoMapper = new DepartmentDtoMapperImpl();
    }

    @Test
    @DisplayName("Test departmentDtoMapper.mapToDepartment()")
    void mapToDepartment_should_getDepartment() {
        Department department = departmentDtoMapper.mapToDepartment(new DepartmentIncomingDto(1, "test"));
        assertTrue(Objects.nonNull(department));
    }

    @Test
    @DisplayName("Test departmentDtoMapper.mapToDepartmentOutGoingDto()")
    void mapToDepartmentOutGoingDto_should_getDepartmentOutGoingDto() {
        DepartmentOutGoingDto departmentOutGoingDto = departmentDtoMapper.mapToDepartmentOutGoingDto(new Department(1, "test"));
        assertTrue(Objects.nonNull(departmentOutGoingDto));
    }

    @Test
    @DisplayName("Test departmentDtoMapper.mapToDepartmentWithProfessorsOutGoingDto()")
    void mapToDepartmentWithProfessorsOutGoingDto_should_getDepartment() throws NoSuchFieldException, IllegalAccessException {
        Field professorDtoMapperField = DepartmentDtoMapperImpl.class.getDeclaredField("professorDtoMapper");
        professorDtoMapperField.setAccessible(true);
        professorDtoMapperField.set(departmentDtoMapper, mockProfessorDtoMapper);


        Department department = new Department();
        department.setId(1);
        department.setName("test");
        Professor professor = new Professor(1, "testN", "testS");
        department.setProfessors(List.of(professor));

        when(mockProfessorDtoMapper.mapToProfessorShortOutGoingDto(department.getProfessors().get(0)))
                .thenReturn(new ProfessorShortOutGoingDto(1, "testN", "testS"));

        DepartmentWithProfessorsOutGoingDto actual = departmentDtoMapper.mapToDepartmentWithProfessorsOutGoingDto(department);
        assertTrue(Objects.nonNull(actual));
    }
}