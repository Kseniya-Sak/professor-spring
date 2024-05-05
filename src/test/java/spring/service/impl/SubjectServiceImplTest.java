package spring.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spring.controller.dto.SubjectIncomingDto;
import spring.controller.dto.SubjectOutGoingDto;
import spring.controller.dto.SubjectWithProfessorsOutGoingDto;
import spring.controller.mapper.SubjectDtoMapper;
import spring.entity.Subject;
import spring.exception.NotFoundException;
import spring.exception.RepositoryException;
import spring.repository.SubjectRepository;

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

class SubjectServiceImplTest {
    private static SubjectRepository mockRepository;
    private static SubjectDtoMapper mockMapper;
    private static SubjectServiceImpl subjectService;

    @BeforeEach
    void setDepartmentService() {
        mockRepository = Mockito.mock(SubjectRepository.class);
        mockMapper = Mockito.mock(SubjectDtoMapper.class);
        subjectService = new SubjectServiceImpl(mockRepository, mockMapper);
    }

    @Test
    @DisplayName("Test subjectService.save() successful")
    void save_should_saveSubject() {
        Subject receivedSubject = new Subject("test", 32);
        Subject savedSubject = new Subject();
        savedSubject.setId(1);
        savedSubject.setName("test");
        savedSubject.setValueOfHours(32);
        SubjectIncomingDto incomingDto = new SubjectIncomingDto(receivedSubject.getName(), receivedSubject.getValueOfHours());

        when(mockRepository.save(receivedSubject)).thenReturn(savedSubject);
        when(mockMapper.map(incomingDto)).thenReturn(receivedSubject);
        when(mockMapper.map(savedSubject)).thenReturn(new SubjectOutGoingDto(1, "test", 32));

        SubjectOutGoingDto actual = subjectService.save(incomingDto);

        assertAll(
                () -> assertTrue(Objects.nonNull(actual)),
                () -> assertEquals(1, actual.getId()),
                () -> assertEquals(32, actual.getValueOfHours())
        );
    }

    @Test
    @DisplayName("Test subjectService.save() throws exception")
    void save_should_throwException_when_nullParameter() {

        SubjectIncomingDto subjectIncomingDtoWithValueOfHoursZero = new SubjectIncomingDto(0,  "test", 0);
        SubjectIncomingDto subjectIncomingDtoWithNameNull = new SubjectIncomingDto(0,  null, 32);
        assertAll(
                () -> assertThrows(RepositoryException.class,
                        () -> subjectService.save(null)),
                () -> assertThrows(RepositoryException.class,
                        () -> subjectService.save(subjectIncomingDtoWithNameNull)),
                () -> assertThrows(RepositoryException.class,
                        () -> subjectService.save(subjectIncomingDtoWithValueOfHoursZero))
        );
    }

    @Test
    @DisplayName("Test subjectService.save() subject's name already exists")
    void save_should_throwException_when_nameAlreadyExist() {
        when(mockRepository.existsByName("test")).thenReturn(true);

        SubjectIncomingDto subjectIncomingDto = new SubjectIncomingDto(0,  "test", 32);

        assertThrows(RepositoryException.class,
                () -> subjectService.save(subjectIncomingDto));
    }

    @Test
    @DisplayName("Test subjectService.update() successful")
    void update_should_updateSubject() {
        Subject subject = new Subject(1,"test", 32);
        SubjectIncomingDto incomingDto = new SubjectIncomingDto(1, "test", 72);

        when(mockRepository.save(subject)).thenReturn(subject);
        when(mockRepository.existsById(1)).thenReturn(true);
        when(mockMapper.map(incomingDto)).thenReturn(subject);
        when(mockMapper.map(subject)).thenReturn(new SubjectOutGoingDto(1, "test", 32));

        subjectService.update(incomingDto);

        verify(mockRepository, times(1)).save(subject);
    }

    @Test
    @DisplayName("Test subjectService.update() throws exception")
    void update_should_throwException_when_subjectWithSuchIdDoesNotExist() {
        SubjectIncomingDto incomingDto = new SubjectIncomingDto(1, "test", 72);

        when(mockRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> subjectService.update(incomingDto));
    }

    @Test
    @DisplayName("Test subjectService.update() throws exception")
    void update_should_throwException_when_nullParameter() {
        SubjectIncomingDto subjectIncomingDto = new SubjectIncomingDto(1, null, 32);
        assertAll(
                () -> assertThrows(RepositoryException.class,
                        () -> subjectService.update(null)),
                () -> assertThrows(RepositoryException.class,
                        () -> subjectService.update(subjectIncomingDto))
        );
    }

    @Test
    @DisplayName("Test subjectService.findById() exists Subject with such Id")
    void findById_should_getSubject() {
        Subject subject = new Subject(1, "test", 32);
        when(mockRepository.findByIdWithProfessor(1))
                .thenReturn(Optional.of(subject));
        when(mockMapper.mapToWithProfessorsOutGoingDto(subject))
                .thenReturn(new SubjectWithProfessorsOutGoingDto(1, "test", 32, null));
        SubjectWithProfessorsOutGoingDto actual = subjectService.findById(1);

        assertAll(
                () -> assertTrue(Objects.nonNull(actual)),
                () -> assertEquals(1, actual.getId())
        );
    }

    @Test
    @DisplayName("Test subjectService.findById() throws exception")
    void findById_should_throwException_when_nullParameter() {
        assertThrows(RepositoryException.class,
                () -> subjectService.findById(null));
    }

    @Test
    @DisplayName("Test subjectService.findById() doesn't exist Subject with such Id")
    void findById_should_throwException_when_subjectDoesNotEexist() {
        when(mockRepository.findByIdWithProfessor(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> subjectService.findById(1));

    }

    @Test
    @DisplayName("Test subjectService.findAll")
    void findAll_should_getSubjects() {
        Subject subject = new Subject(1, "test", 72);
        SubjectOutGoingDto subjectOutGoingDto = new SubjectOutGoingDto(1, "test", 72);
        when(mockRepository.findAll()).thenReturn(List.of(subject));
        when(mockMapper.map(subject)).thenReturn(subjectOutGoingDto);

        List<SubjectOutGoingDto> actual = subjectService.findAll();
        assertAll(
                () -> assertTrue(Objects.nonNull(actual)),
                () -> assertEquals(1, actual.get(0).getId())
        );

    }

    @Test
    @DisplayName("Test subjectService.deleteById successful")
    void deleteById_should_deleteSubject() {
        Subject subject = new Subject(1, "test", 32);
        when(mockRepository.existsById(1))
                .thenReturn(true);
        when(mockMapper.mapToWithProfessorsOutGoingDto(subject))
                .thenReturn(new SubjectWithProfessorsOutGoingDto(1, "test", 32, null));

        subjectService.deleteById(1);

        verify(mockRepository,times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Test subjectService.deleteById throws Exception when id==null")
    void deleteById_should_throwException_when_nullParameter() {
        assertThrows(RepositoryException.class,
                () -> subjectService.deleteById(null));

    }

    @Test
    @DisplayName("Test subjectService.deleteById when subject with such id doesn't exitst")
    void deleteById_should_throwException_when_departmentDoesNotExist() {
        when(mockRepository.findByIdWithProfessor(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> subjectService.deleteById(1));

    }
}