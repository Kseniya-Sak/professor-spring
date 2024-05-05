package spring.controller.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import spring.controller.dto.ProfessorShortOutGoingDto;
import spring.controller.dto.SubjectIncomingDto;
import spring.controller.dto.SubjectOutGoingDto;
import spring.controller.dto.SubjectWithProfessorsOutGoingDto;
import spring.entity.Professor;
import spring.entity.Subject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class SubjectDtoMapperTest {
    private static SubjectDtoMapper subjectDtoMapper;
    private static ProfessorDtoMapper mockProfessorDtoMapper;

    @BeforeEach
    void setUp() {
        subjectDtoMapper = new SubjectDtoMapperImpl();
        mockProfessorDtoMapper = Mockito.mock(ProfessorDtoMapperImpl.class);
    }
    @Test
    @DisplayName("Test subjectDtoMapper.mapToSubjectOutGoingDto()")
    void mapToSubjectOutGoingDto_should_getSubjectOutGoingDto() {
        SubjectOutGoingDto actual = subjectDtoMapper.map(new Subject(1, "test", 32));
        assertTrue(Objects.nonNull(actual));
    }

    @Test
    @DisplayName("Test subjectDtoMapper.mapToSubject()")
    void mapToSubject_should_getSubject() {
        Subject actual = subjectDtoMapper.map(new SubjectIncomingDto(1, "test", 32));
        assertTrue(Objects.nonNull(actual));
    }

    @Test
    @DisplayName("Test subjectDtoMapper.mapToDepartmentWithProfessorsOutGoingDto()")
    void mapToDepartmentWithProfessorsOutGoingDto_should_getSubjectWithProfessorsOutGoingDto()
            throws NoSuchFieldException, IllegalAccessException {
        Field professorDtoMapperField = SubjectDtoMapperImpl.class.getDeclaredField("professorDtoMapper");
        professorDtoMapperField.setAccessible(true);
        professorDtoMapperField.set(subjectDtoMapper, mockProfessorDtoMapper);

        Subject subject = new Subject(1, "test", 32);
        Professor professor = new Professor(1, "testN", "testS");
        subject.setProfessors(List.of(professor));

        when(mockProfessorDtoMapper.mapToProfessorShortOutGoingDto(subject.getProfessors().get(0)))
                .thenReturn(new ProfessorShortOutGoingDto(1, "testN", "testS"));

        SubjectWithProfessorsOutGoingDto actual = subjectDtoMapper.mapToWithProfessorsOutGoingDto(subject);
        assertTrue(Objects.nonNull(actual));
    }
}