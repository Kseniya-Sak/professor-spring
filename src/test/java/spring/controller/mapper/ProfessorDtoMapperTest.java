package spring.controller.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spring.controller.dto.DepartmentOutGoingDto;
import spring.controller.dto.ProfessorOutGoingDto;
import spring.controller.dto.ProfessorShortOutGoingDto;
import spring.controller.dto.ProfessorWithSubjectsOutGoingDto;
import spring.controller.dto.SubjectOutGoingDto;
import spring.entity.Department;
import spring.entity.Professor;
import spring.entity.Subject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ProfessorDtoMapperTest {
    private static ProfessorDtoMapper professorDtoMapper;
    private static DepartmentDtoMapper mockDepartmentDtoMapper;
    private static SubjectDtoMapper mockSubjectDtoMapper;

    @BeforeEach
    void setUp() {
        mockDepartmentDtoMapper = Mockito.mock(DepartmentDtoMapperImpl.class);
        mockSubjectDtoMapper = Mockito.mock(SubjectDtoMapperImpl.class);
        professorDtoMapper = new ProfessorDtoMapperImpl();
    }

    @Test
    @DisplayName("Test professorDtoMapper.mapToProfessorShortOutGoingDto()")
    void mapToProfessorShortOutGoingDto_should_getProfessorShortOutGoingDto() {
        ProfessorShortOutGoingDto actual = professorDtoMapper.mapToProfessorShortOutGoingDto(new Professor(1, "testN", "testS"));
        assertTrue(Objects.nonNull(actual));
    }

    @Test
    @DisplayName("Test professorDtoMapper.mapToProfessorOutGoingDto()")
    void mapToProfessorOutGoingDto_should_getProfessorOutGoingDto() throws NoSuchFieldException, IllegalAccessException {
        Field professorDtoMapperField = ProfessorDtoMapperImpl.class.getDeclaredField("departmentDtoMapper");
        professorDtoMapperField.setAccessible(true);
        professorDtoMapperField.set(professorDtoMapper, mockDepartmentDtoMapper);

        Department department = new Department(1, "test");
        Professor professor = new Professor(1, "testN", "testS", department);

        when(mockDepartmentDtoMapper.mapToDepartmentOutGoingDto(professor.getDepartment()))
                .thenReturn(new DepartmentOutGoingDto(1, "test"));

        ProfessorOutGoingDto actual = professorDtoMapper.map(professor);
        assertTrue(Objects.nonNull(actual));
    }

    @Test
    @DisplayName("Test professorDtoMapper.mapToProfessorWithSubjectsOutGoingDto()")
    void mapToProfessorWithSubjectsOutGoingDto_should_getProfessorWithSubjectsOutGoingDto()
            throws NoSuchFieldException, IllegalAccessException {
        Field professorDtoMapperField = ProfessorDtoMapperImpl.class.getDeclaredField("departmentDtoMapper");
        professorDtoMapperField.setAccessible(true);
        professorDtoMapperField.set(professorDtoMapper, mockDepartmentDtoMapper);

        Field subjectDtoMapperField = ProfessorDtoMapperImpl.class.getDeclaredField("subjectDtoMapper");
        subjectDtoMapperField.setAccessible(true);
        subjectDtoMapperField.set(professorDtoMapper, mockSubjectDtoMapper);

        Department department = new Department(1, "test");
        Subject subject = new Subject(1, "test", 32);
        Professor professor = new Professor(1, "testN", "testS", department);
        professor.setSubjects(List.of(subject));

        when(mockDepartmentDtoMapper.mapToDepartmentOutGoingDto(professor.getDepartment()))
                .thenReturn(new DepartmentOutGoingDto(1, "test"));
        when(mockSubjectDtoMapper.map(subject)).thenReturn(new SubjectOutGoingDto(1, "test", 32));

        ProfessorWithSubjectsOutGoingDto actual = professorDtoMapper.mapToProfessorWithSubjectsOutGoingDto(professor);
        assertTrue(Objects.nonNull(actual));
    }
}