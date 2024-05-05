package spring.controller.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.controller.dto.ProfessorOutGoingDto;
import spring.controller.dto.ProfessorShortOutGoingDto;
import spring.controller.dto.ProfessorWithSubjectsIncomingDto;
import spring.controller.dto.ProfessorWithSubjectsOutGoingDto;
import spring.controller.dto.SubjectIncomingDto;
import spring.controller.dto.SubjectOutGoingDto;
import spring.entity.Professor;
import spring.entity.Subject;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-05T22:44:22+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class ProfessorDtoMapperImpl implements ProfessorDtoMapper {

    @Autowired
    private DepartmentDtoMapper departmentDtoMapper;
    @Autowired
    private SubjectDtoMapper subjectDtoMapper;

    @Override
    public ProfessorOutGoingDto map(Professor professor) {
        if ( professor == null ) {
            return null;
        }

        ProfessorOutGoingDto professorOutGoingDto = new ProfessorOutGoingDto();

        professorOutGoingDto.setId( professor.getId() );
        professorOutGoingDto.setName( professor.getName() );
        professorOutGoingDto.setSurname( professor.getSurname() );
        professorOutGoingDto.setDepartment( departmentDtoMapper.mapToDepartmentOutGoingDto( professor.getDepartment() ) );

        return professorOutGoingDto;
    }

    @Override
    public ProfessorShortOutGoingDto mapToProfessorShortOutGoingDto(Professor professor) {
        if ( professor == null ) {
            return null;
        }

        ProfessorShortOutGoingDto professorShortOutGoingDto = new ProfessorShortOutGoingDto();

        professorShortOutGoingDto.setId( professor.getId() );
        professorShortOutGoingDto.setName( professor.getName() );
        professorShortOutGoingDto.setSurname( professor.getSurname() );

        return professorShortOutGoingDto;
    }

    @Override
    public ProfessorWithSubjectsOutGoingDto mapToProfessorWithSubjectsOutGoingDto(Professor professor) {
        if ( professor == null ) {
            return null;
        }

        ProfessorWithSubjectsOutGoingDto professorWithSubjectsOutGoingDto = new ProfessorWithSubjectsOutGoingDto();

        professorWithSubjectsOutGoingDto.setId( professor.getId() );
        professorWithSubjectsOutGoingDto.setName( professor.getName() );
        professorWithSubjectsOutGoingDto.setSurname( professor.getSurname() );
        professorWithSubjectsOutGoingDto.setDepartment( departmentDtoMapper.mapToDepartmentOutGoingDto( professor.getDepartment() ) );
        professorWithSubjectsOutGoingDto.setSubjects( subjectListToSubjectOutGoingDtoList( professor.getSubjects() ) );

        return professorWithSubjectsOutGoingDto;
    }

    @Override
    public Professor mapToProfessorWithSubjects(ProfessorWithSubjectsIncomingDto incomingDto) {
        if ( incomingDto == null ) {
            return null;
        }

        Professor professor = new Professor();

        professor.setId( incomingDto.getId() );
        professor.setName( incomingDto.getName() );
        professor.setSurname( incomingDto.getSurname() );
        professor.setSubjects( subjectIncomingDtoListToSubjectList( incomingDto.getSubjects() ) );
        professor.setDepartment( departmentDtoMapper.mapToDepartment( incomingDto.getDepartment() ) );

        return professor;
    }

    protected List<SubjectOutGoingDto> subjectListToSubjectOutGoingDtoList(List<Subject> list) {
        if ( list == null ) {
            return null;
        }

        List<SubjectOutGoingDto> list1 = new ArrayList<SubjectOutGoingDto>( list.size() );
        for ( Subject subject : list ) {
            list1.add( subjectDtoMapper.map( subject ) );
        }

        return list1;
    }

    protected List<Subject> subjectIncomingDtoListToSubjectList(List<SubjectIncomingDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Subject> list1 = new ArrayList<Subject>( list.size() );
        for ( SubjectIncomingDto subjectIncomingDto : list ) {
            list1.add( subjectDtoMapper.map( subjectIncomingDto ) );
        }

        return list1;
    }
}
