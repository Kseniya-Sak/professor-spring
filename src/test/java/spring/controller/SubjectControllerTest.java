package spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import spring.controller.dto.DepartmentOutGoingDto;
import spring.controller.dto.ExceptionDto;
import spring.controller.dto.ProfessorOutGoingDto;
import spring.controller.dto.SubjectIncomingDto;
import spring.controller.dto.SubjectOutGoingDto;
import spring.controller.dto.SubjectWithProfessorsOutGoingDto;
import spring.exception.NotFoundException;
import spring.exception.RepositoryException;
import spring.service.SubjectService;
import spring.service.impl.SubjectServiceImpl;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SubjectControllerTest {
    private static SubjectService mockService;
    private static SubjectController controller;

    @BeforeEach
    void setUp() {
        mockService = mock(SubjectServiceImpl.class);
        controller = new SubjectController(mockService);
    }


    @Test
    @DisplayName("Test subjectController.getSubjectById successful")
    void getSubjectById_should_getSubject() {
        ProfessorOutGoingDto professor = new ProfessorOutGoingDto();
        professor.setId(1);
        professor.setName("testN");
        professor.setSurname("testS");
        professor.setDepartment(new DepartmentOutGoingDto(1, "test"));

        SubjectWithProfessorsOutGoingDto subject = new SubjectWithProfessorsOutGoingDto();
        subject.setId(1);
        subject.setName("test");
        subject.setValueOfHours(32);
        subject.setProfessors(List.of(professor));

        when(mockService.findById(1)).thenReturn(subject);

        SubjectWithProfessorsOutGoingDto actual = controller.getSubjectById(1);
        ProfessorOutGoingDto actualProfessor = actual.getProfessors().get(0);

        assertAll(
                () -> assertEquals(1, actual.getId()),
                () -> assertEquals("test", actual.getName()),
                () -> assertEquals(32, actual.getValueOfHours()),
                () -> assertEquals(1, actualProfessor.getId()),
                () -> assertEquals("testN", actualProfessor.getName()),
                () -> assertEquals("testS", actualProfessor.getSurname())
        );
    }

    @Test
    @DisplayName("Test subjectController.getSubjects successful")
    void getSubjects_should_getAllSubjects() {
        SubjectOutGoingDto subject = new SubjectOutGoingDto();
        subject.setId(1);
        subject.setName("test");
        subject.setValueOfHours(32);
        List<SubjectOutGoingDto> subjects = List.of(subject);

        when(mockService.findAll()).thenReturn(subjects);

        List<SubjectOutGoingDto> actual = controller.getSubjects();

        assertEquals(1, actual.size());
    }

    @Test
    @DisplayName("Test subjectController.createSubject successful")
    void createSubject_should_getSavedSubject() {
        SubjectIncomingDto subjectIncomingDto = new SubjectIncomingDto();
        subjectIncomingDto.setName("test");
        subjectIncomingDto.setValueOfHours(32);
        SubjectOutGoingDto subjectOutGoingDto = new SubjectOutGoingDto();
        subjectOutGoingDto.setId(1);
        subjectOutGoingDto.setName("test");
        subjectOutGoingDto.setValueOfHours(32);

        when(mockService.save(subjectIncomingDto)).thenReturn(subjectOutGoingDto);
        SubjectOutGoingDto actual = controller.createSubject(subjectIncomingDto);
        assertEquals(1, actual.getId());
    }

    @Test
    @DisplayName("Test subjectController.updateSubject successful")
    void updateSubject_should_updateSubject() {
        SubjectIncomingDto subjectIncomingDto = new SubjectIncomingDto();
        subjectIncomingDto.setName("test");
        subjectIncomingDto.setValueOfHours(32);

        controller.updateSubject(1, subjectIncomingDto);
        subjectIncomingDto.setId(1);
        verify(mockService, times(1)).update(subjectIncomingDto);
    }

    @Test
    @DisplayName("Test subjectController.deleteSubject successful")
    void deleteSubject_should_deleteSubject() {
        controller.deleteSubject(1);
        verify(mockService, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Test subjectController.catchNotFoundException")
    void catchNotFoundException() {
        NotFoundException e = new NotFoundException("Test");
        ResponseEntity<ExceptionDto> actual = controller.catchNotFoundException(e);
        assertTrue(Objects.nonNull(actual));
    }

    @Test
    @DisplayName("Test subjectController.catchRepositoryException")
    void catchRepositoryException() {
        RepositoryException e = new RepositoryException("Test");
        ResponseEntity<ExceptionDto> actual = controller.catchRepositoryException(e);
        assertTrue(Objects.nonNull(actual));
    }
}