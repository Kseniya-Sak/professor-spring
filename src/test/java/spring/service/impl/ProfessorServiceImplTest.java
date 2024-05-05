package spring.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spring.controller.dto.DepartmentIncomingDto;
import spring.controller.dto.ProfessorOutGoingDto;
import spring.controller.dto.ProfessorWithSubjectsIncomingDto;
import spring.controller.dto.ProfessorWithSubjectsOutGoingDto;
import spring.controller.mapper.ProfessorDtoMapper;
import spring.entity.Department;
import spring.entity.Professor;
import spring.entity.Subject;
import spring.exception.NotFoundException;
import spring.exception.RepositoryException;
import spring.repository.ProfessorRepository;
import spring.service.ProfessorService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProfessorServiceImplTest {
    private static ProfessorRepository mockRepository;
    private static ProfessorDtoMapper mockMapper;
    private static ProfessorService professorService;

    @BeforeEach
    void setUp() {
        mockRepository = mock(ProfessorRepository.class);
        mockMapper = mock(ProfessorDtoMapper.class);
        professorService = new ProfessorServiceImpl(mockRepository, mockMapper);
    }

    @Test
    @DisplayName("Test professorService.save() successful")
    void save_should_saveProfessor() {
        Professor receivedProfessor = new Professor("testN", "testS", null);
        Professor savedProfessor = new Professor(1, "testN", "testS", null);
        ProfessorWithSubjectsIncomingDto professorIncomingDto = new ProfessorWithSubjectsIncomingDto(0, "testN", "testS", null, null);
        ProfessorWithSubjectsOutGoingDto professorOutGoingDto = new ProfessorWithSubjectsOutGoingDto(1, "testN", "testS", null, null);
        when(mockRepository.save(receivedProfessor))
                .thenReturn(savedProfessor);

        when(mockMapper.mapToProfessorWithSubjects(professorIncomingDto))
                .thenReturn(receivedProfessor);
        when(mockMapper.mapToProfessorWithSubjectsOutGoingDto(savedProfessor))
                .thenReturn(professorOutGoingDto);

        ProfessorWithSubjectsOutGoingDto actual = professorService.save(professorIncomingDto);

        assertAll(
                () -> assertTrue(Objects.nonNull(actual)),
                () -> assertEquals(1, actual.getId())
        );
    }

    @Test
    @DisplayName("Test professorService.save() throws exception")
    void save_should_throwException_when_nullParameter() {
        ProfessorWithSubjectsIncomingDto professor = new ProfessorWithSubjectsIncomingDto();
        assertAll(
                () -> assertThrows(RepositoryException.class,
                        () -> professorService.save(null)),
                () -> assertThrows(RepositoryException.class,
                        () -> professorService.save(professor))
        );
    }

    @Test
    @DisplayName("Test professorService.update() successful")
    void update_should_updateProfessor() {
        Professor receivedProfessor = new Professor();
        receivedProfessor.setId(1);
        receivedProfessor.setName("testN");
        receivedProfessor.setSurname("testS");
        receivedProfessor.setDepartment(new Department());
        receivedProfessor.setSubjects(List.of(new Subject()));

        Professor savedProfessor = new Professor(1, "testN", "testS", null);
        ProfessorWithSubjectsIncomingDto professorIncomingDto = new ProfessorWithSubjectsIncomingDto(receivedProfessor.getId(),
                receivedProfessor.getName(), receivedProfessor.getSurname(), new DepartmentIncomingDto(), null);
        ProfessorWithSubjectsOutGoingDto professorOutGoingDto = new ProfessorWithSubjectsOutGoingDto(1, "testN", "testS", null, null);
        when(mockRepository.save(receivedProfessor))
                .thenReturn(savedProfessor);
        when(mockRepository.existsById(1))
                .thenReturn(true);

        when(mockMapper.mapToProfessorWithSubjects(professorIncomingDto))
                .thenReturn(receivedProfessor);
        when(mockMapper.mapToProfessorWithSubjectsOutGoingDto(savedProfessor))
                .thenReturn(professorOutGoingDto);

        professorService.update(professorIncomingDto);

        verify(mockRepository, times(1)).save(receivedProfessor);
    }

    @Test
    @DisplayName("Test professorService.update() throws exception")
    void update_should_throwException_when_professorWithSuchIdDoesNotExist() {
        Professor receivedProfessor = new Professor();
        receivedProfessor.setId(1);
        receivedProfessor.setName("testN");
        receivedProfessor.setSurname("testS");
        receivedProfessor.setDepartment(new Department());
        receivedProfessor.setSubjects(List.of(new Subject()));

        ProfessorWithSubjectsIncomingDto professorIncomingDto = new ProfessorWithSubjectsIncomingDto(receivedProfessor.getId(),
                receivedProfessor.getName(), receivedProfessor.getSurname(), new DepartmentIncomingDto(), null);
        when(mockRepository.existsById(1))
                .thenReturn(false);

        assertThrows(NotFoundException.class, () -> professorService.update(professorIncomingDto));
    }

    @Test
    @DisplayName("Test professorService.update() throws exception")
    void update_should_throwException_when_nullParameter() {
        ProfessorWithSubjectsIncomingDto professor = new ProfessorWithSubjectsIncomingDto();
        assertAll(
                () -> assertThrows(RepositoryException.class,
                        () -> professorService.update(null)),
                () -> assertThrows(RepositoryException.class,
                        () -> professorService.update(professor))
        );
    }

    @Test
    @DisplayName("Test professorService.findById() exists Professor with such Id")
    void findById_should_getProfessor() {
        Professor receivedProfessor = new Professor(1, "testN", "testS", null);
        when(mockRepository.findByIdWithSubject(1))
                .thenReturn(Optional.of(receivedProfessor));
        when(mockMapper.mapToProfessorWithSubjectsOutGoingDto(receivedProfessor))
                .thenReturn(new ProfessorWithSubjectsOutGoingDto(1, "testN", "testS", null, null));

        ProfessorWithSubjectsOutGoingDto actual = professorService.findById(1);
        assertAll(
                () -> assertTrue(Objects.nonNull(actual)),
                () -> assertEquals(1, actual.getId())
        );


    }

    @Test
    @DisplayName("Test professorService.findById() throws exception")
    void findById_should_throwException_when_nullParameter() {
        assertThrows(RepositoryException.class, () -> professorService.findById(null));
    }

    @Test
    @DisplayName("Test professorService.findById() doesn't exist Professor with such Id")
    void findById_should_throwException_when_professorDoesNotEexist() {
        when(mockRepository.findByIdWithSubject(1))
                        .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> professorService.findById(1));
    }

    @Test
    @DisplayName("Test professorService.findAll")
    void findAll_should_getProfessors() {
        Professor professor = new Professor(1, "testN", "testS");
        List<Professor> professors = List.of(professor);
        when(mockRepository.findAll()).thenReturn(professors);
        when(mockMapper.map(professor)).thenReturn(new ProfessorOutGoingDto(1, "testN", "testS", null));
        List<ProfessorOutGoingDto> professorOutGoingDtos = professorService.findAll();

        assertAll(
                () -> assertEquals(1, professorOutGoingDtos.size()),
                () -> assertEquals(1, professorOutGoingDtos.get(0).getId())
        );
    }

    @Test
    @DisplayName("Test professorService.deleteById successful")
    void deleteById_should_deleteProfessor() {
        Professor professor = new Professor(1, "testN", "testS");
        when(mockRepository.existsById(1)).thenReturn(true);
        when(mockMapper.mapToProfessorWithSubjectsOutGoingDto(professor))
                .thenReturn(new ProfessorWithSubjectsOutGoingDto(1, "testN", "testS", null, null));
        professorService.deleteById(1);

        Mockito.verify(mockRepository,times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Test professorService.deleteById throws Exception when id==null")
    void deleteById_should_throwException_when_nullParameter() {
        assertThrows(RepositoryException.class, () -> professorService.deleteById(null));
    }

    @Test
    @DisplayName("Test professorService.deleteById throws Exception when professor doesn't exist")
    void deleteById_should_throwException_when_notSuchId() {
        when(mockRepository.findByIdWithSubject(1)).thenReturn(Optional.empty());
        assertThrows(RepositoryException.class, () -> professorService.deleteById(null));
    }


}