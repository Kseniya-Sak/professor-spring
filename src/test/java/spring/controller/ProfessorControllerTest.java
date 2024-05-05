package spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import spring.controller.dto.DepartmentIncomingDto;
import spring.controller.dto.DepartmentOutGoingDto;
import spring.controller.dto.ExceptionDto;
import spring.controller.dto.ProfessorOutGoingDto;
import spring.controller.dto.ProfessorWithSubjectsIncomingDto;
import spring.controller.dto.ProfessorWithSubjectsOutGoingDto;
import spring.controller.dto.SubjectIncomingDto;
import spring.controller.dto.SubjectOutGoingDto;
import spring.exception.NotFoundException;
import spring.exception.RepositoryException;
import spring.service.ProfessorService;
import spring.service.impl.ProfessorServiceImpl;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProfessorControllerTest {

    private static ProfessorService mockService;
    private static ProfessorController controller;

    @BeforeEach
    void setUp() {
        mockService = Mockito.mock(ProfessorServiceImpl.class);
        controller = new ProfessorController(mockService);
    }

    @Test
    @DisplayName("Test professorController.getProfessorById successful")
    void getProfessorById_should_getProfessorById() {
        DepartmentOutGoingDto department = new DepartmentOutGoingDto();
        department.setId(1);
        department.setName("test");
        SubjectOutGoingDto subject = new SubjectOutGoingDto();
        subject.setId(1);
        subject.setName("test");
        subject.setValueOfHours(32);

        ProfessorWithSubjectsOutGoingDto professor = new ProfessorWithSubjectsOutGoingDto();
        professor.setId(1);
        professor.setName("testN");
        professor.setSurname("testS");
        professor.setDepartment(department);
        professor.setSubjects(List.of(subject));

        when(mockService.findById(1)).thenReturn(professor);

        ProfessorWithSubjectsOutGoingDto actual = controller.getProfessorById(1);

        assertTrue(Objects.nonNull(actual));

    }

    @Test
    @DisplayName("Test professorController.getProfessors successful")
    void getProfessors_should_getAllProfessors() {
        DepartmentOutGoingDto department = new DepartmentOutGoingDto();
        department.setId(1);
        department.setName("test");
        ProfessorOutGoingDto professor = new ProfessorOutGoingDto();
        professor.setId(1);
        professor.setName("testN");
        professor.setSurname("testS");
        professor.setDepartment(department);

        List<ProfessorOutGoingDto> professors = List.of(professor);

        when(mockService.findAll()).thenReturn(professors);

        List<ProfessorOutGoingDto> actual = controller.getProfessors();

        assertAll(
                () -> assertEquals(1, actual.size()),
                () -> assertEquals(1, actual.get(0).getId()),
                () -> assertEquals("testN", actual.get(0).getName()),
                () -> assertEquals("testS", actual.get(0).getSurname()),
                () -> assertEquals(1, actual.get(0).getDepartment().getId()),
                () -> assertEquals("test", actual.get(0).getDepartment().getName())
        );
    }

    @Test
    @DisplayName("Test professorController.createProfessor successful")
    void createProfessor_should_getSavedProfessor() {
        DepartmentIncomingDto departmentIncomingDto = new DepartmentIncomingDto();
        departmentIncomingDto.setId(1);
        departmentIncomingDto.setName("test");
        SubjectIncomingDto subjectIncomingDto = new SubjectIncomingDto();
        subjectIncomingDto.setId(1);
        subjectIncomingDto.setName("test");
        subjectIncomingDto.setValueOfHours(32);
        ProfessorWithSubjectsIncomingDto professorIncomingDto = new ProfessorWithSubjectsIncomingDto();
        professorIncomingDto.setName("testN");
        professorIncomingDto.setSurname("testS");
        professorIncomingDto.setDepartment(departmentIncomingDto);
        professorIncomingDto.setSubjects(List.of(subjectIncomingDto));

        DepartmentOutGoingDto departmentOutGoingDto = new DepartmentOutGoingDto();
        departmentOutGoingDto.setId(1);
        departmentOutGoingDto.setName("test");
        SubjectOutGoingDto subjectOutGoingDto = new SubjectOutGoingDto();
        subjectOutGoingDto.setId(1);
        subjectOutGoingDto.setName("test");
        subjectOutGoingDto.setValueOfHours(32);

        ProfessorWithSubjectsOutGoingDto professorWithSubjectsOutGoingDto = new ProfessorWithSubjectsOutGoingDto();
        professorWithSubjectsOutGoingDto.setId(1);
        professorWithSubjectsOutGoingDto.setName("testN");
        professorWithSubjectsOutGoingDto.setSurname("testS");
        professorWithSubjectsOutGoingDto.setDepartment(departmentOutGoingDto);
        professorWithSubjectsOutGoingDto.setSubjects(List.of(subjectOutGoingDto));

        when(mockService.save(professorIncomingDto)).thenReturn(professorWithSubjectsOutGoingDto);

        ProfessorWithSubjectsOutGoingDto actual = controller.createProfessor(professorIncomingDto);

        SubjectOutGoingDto actualSubject = actual.getSubjects().get(0);

        assertAll(
                () -> assertEquals(1, actual.getId()),
                () -> assertEquals("testN", actual.getName()),
                () -> assertEquals("testS", actual.getSurname()),
                () -> assertEquals(1, actual.getDepartment().getId()),
                () -> assertEquals("test", actual.getDepartment().getName()),
                () -> assertEquals(1, actualSubject.getId()),
                () -> assertEquals("test", actualSubject.getName()),
                () -> assertEquals(32, actualSubject.getValueOfHours())
        );
    }

    @Test
    @DisplayName("Test professorController.updateProfessor successful")
    void updateProfessor_should_updateProfessor() {
        DepartmentIncomingDto departmentIncomingDto = new DepartmentIncomingDto();
        departmentIncomingDto.setId(1);
        departmentIncomingDto.setName("test");
        SubjectIncomingDto subjectIncomingDto = new SubjectIncomingDto();
        subjectIncomingDto.setId(1);
        subjectIncomingDto.setName("test");
        subjectIncomingDto.setValueOfHours(32);
        ProfessorWithSubjectsIncomingDto professorIncomingDto = new ProfessorWithSubjectsIncomingDto();
        professorIncomingDto.setName("testN");
        professorIncomingDto.setSurname("testS");
        professorIncomingDto.setDepartment(departmentIncomingDto);
        professorIncomingDto.setSubjects(List.of(subjectIncomingDto));

        controller.updateProfessor(1, professorIncomingDto);
        professorIncomingDto.setId(1);
        verify(mockService, times(1)).update(professorIncomingDto);

    }

    @Test
    @DisplayName("Test professorController.deleteProfessor successful")
    void deleteProfessor_should_deleteProfessor() {
        controller.deleteProfessor(1);
        verify(mockService, times(1)).deleteById(1);
    }


    @Test
    @DisplayName("Test professorController.catchNotFoundException")
    void catchNotFoundException() {
        NotFoundException e = new NotFoundException("Test");
        ResponseEntity<ExceptionDto> actual = controller.catchNotFoundException(e);
        assertTrue(Objects.nonNull(actual));
    }

    @Test
    @DisplayName("Test professorController.catchRepositoryException")
    void catchRepositoryException() {
        RepositoryException e = new RepositoryException("Test");
        ResponseEntity<ExceptionDto> actual = controller.catchRepositoryException(e);
        assertTrue(Objects.nonNull(actual));
    }
}