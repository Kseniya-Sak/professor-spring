package spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import spring.controller.dto.DepartmentIncomingDto;
import spring.controller.dto.DepartmentOutGoingDto;
import spring.controller.dto.DepartmentWithProfessorsOutGoingDto;
import spring.controller.dto.ExceptionDto;
import spring.controller.dto.ProfessorShortOutGoingDto;
import spring.exception.NotFoundException;
import spring.exception.RepositoryException;
import spring.service.DepartmentService;
import spring.service.impl.DepartmentServiceImpl;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DepartmentControllerTest {
    private static DepartmentService mockService;
    private static DepartmentController controller;

    @BeforeEach
    void setUpEach() {
        mockService = Mockito.mock(DepartmentServiceImpl.class);
        controller = new DepartmentController(mockService);
    }

    @Test
    @DisplayName("Test departmentController.getDepartmentById successful")
    void getDepartmentById_should_getDepartment() {
        ProfessorShortOutGoingDto professor = new ProfessorShortOutGoingDto();
        professor.setId(1);
        professor.setName("testN");
        professor.setSurname("testS");
        DepartmentWithProfessorsOutGoingDto outGoingDto = new DepartmentWithProfessorsOutGoingDto();
        outGoingDto.setId(1);
        outGoingDto.setName("test");
        outGoingDto.setProfessors(List.of(professor));

        when(mockService.findById(1)).thenReturn(outGoingDto);
        DepartmentWithProfessorsOutGoingDto actual = controller.getDepartmentById(1);
        ProfessorShortOutGoingDto actualProfessor = actual.getProfessors().get(0);

        assertAll(
                () -> assertTrue(Objects.nonNull(actual.getProfessors())),
                () -> assertEquals(1, actual.getId()),
                () -> assertEquals("test", actual.getName()),
                () -> assertEquals(1, actualProfessor.getId()),
                () -> assertEquals("testN", actualProfessor.getName()),
                () -> assertEquals("testS", actualProfessor.getSurname())
        );
    }

    @Test
    @DisplayName("Test departmentController.getDepartment throwException")
    void getDepartmentById_should_throwException_when_departmentIdDoesNotExist() {
        when(mockService.findById(1)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> controller.getDepartmentById(1));
    }

    @Test
    @DisplayName("Test departmentController.getDepartments")
    void getDepartments_should_getDepartments() {
        List<DepartmentOutGoingDto> departments = List.of(new DepartmentOutGoingDto(1, "test"));
        when(mockService.findAll()).thenReturn(departments);

        List<DepartmentOutGoingDto> actual = controller.getDepartments();

        assertEquals(1, actual.size());
    }

    @Test
    @DisplayName("Test departmentController.createDepartment successful")
    void createDepartment_should_getSavedDepartment() {
        DepartmentIncomingDto incomingDto = new DepartmentIncomingDto();
        incomingDto.setName("test");
        DepartmentOutGoingDto outGoingDto = new DepartmentOutGoingDto();
        outGoingDto.setId(1);
        outGoingDto.setName("test");

        when(mockService.save(incomingDto)).thenReturn(outGoingDto);

        DepartmentOutGoingDto actual = controller.createDepartment(incomingDto);

        assertAll(
                () -> assertEquals(1, actual.getId()),
                () -> assertEquals("test", actual.getName())
        );
    }

    @Test
    @DisplayName("Test departmentController.updateDepartment successful")
    void updateDepartment_should_updateDepartment() {
        DepartmentIncomingDto incomingDto = new DepartmentIncomingDto();
        incomingDto.setName("test");
        controller.updateDepartment(1, incomingDto);
        incomingDto.setId(1);
        verify(mockService, times(1)).update(incomingDto);
    }

    @Test
    @DisplayName("Test departmentController.deleteDepartment successful")
    void deleteDepartment_should_deleteDepartment() {
        controller.deleteDepartment(1);
        verify(mockService, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Test departmentController.catchNotFoundException")
    void catchNotFoundException() {
        NotFoundException e = new NotFoundException("Test");
        ResponseEntity<ExceptionDto> actual = controller.catchNotFoundException(e);
        assertTrue(Objects.nonNull(actual));
    }

    @Test
    @DisplayName("Test departmentController.catchRepositoryException")
    void catchRepositoryException() {
        RepositoryException e = new RepositoryException("Test");
        ResponseEntity<ExceptionDto> actual = controller.catchRepositoryException(e);
        assertTrue(Objects.nonNull(actual));
    }
}