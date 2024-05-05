package spring.controller.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.controller.dto.ProfessorOutGoingDto;
import spring.controller.dto.SubjectIncomingDto;
import spring.controller.dto.SubjectOutGoingDto;
import spring.controller.dto.SubjectWithProfessorsOutGoingDto;
import spring.entity.Professor;
import spring.entity.Subject;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-05T22:44:22+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class SubjectDtoMapperImpl implements SubjectDtoMapper {

    @Autowired
    private ProfessorDtoMapper professorDtoMapper;

    @Override
    public Subject map(SubjectIncomingDto incomingDto) {
        if ( incomingDto == null ) {
            return null;
        }

        Subject subject = new Subject();

        subject.setId( incomingDto.getId() );
        subject.setName( incomingDto.getName() );
        subject.setValueOfHours( incomingDto.getValueOfHours() );

        return subject;
    }

    @Override
    public SubjectOutGoingDto map(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectOutGoingDto subjectOutGoingDto = new SubjectOutGoingDto();

        subjectOutGoingDto.setId( subject.getId() );
        subjectOutGoingDto.setName( subject.getName() );
        subjectOutGoingDto.setValueOfHours( subject.getValueOfHours() );

        return subjectOutGoingDto;
    }

    @Override
    public SubjectWithProfessorsOutGoingDto mapToWithProfessorsOutGoingDto(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectWithProfessorsOutGoingDto subjectWithProfessorsOutGoingDto = new SubjectWithProfessorsOutGoingDto();

        subjectWithProfessorsOutGoingDto.setId( subject.getId() );
        subjectWithProfessorsOutGoingDto.setName( subject.getName() );
        subjectWithProfessorsOutGoingDto.setValueOfHours( subject.getValueOfHours() );
        subjectWithProfessorsOutGoingDto.setProfessors( professorListToProfessorOutGoingDtoList( subject.getProfessors() ) );

        return subjectWithProfessorsOutGoingDto;
    }

    protected List<ProfessorOutGoingDto> professorListToProfessorOutGoingDtoList(List<Professor> list) {
        if ( list == null ) {
            return null;
        }

        List<ProfessorOutGoingDto> list1 = new ArrayList<ProfessorOutGoingDto>( list.size() );
        for ( Professor professor : list ) {
            list1.add( professorDtoMapper.map( professor ) );
        }

        return list1;
    }
}
