package spring.controller.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.controller.dto.DepartmentIncomingDto;
import spring.controller.dto.DepartmentOutGoingDto;
import spring.controller.dto.DepartmentWithProfessorsOutGoingDto;
import spring.controller.dto.ProfessorShortOutGoingDto;
import spring.entity.Department;
import spring.entity.Professor;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-05T22:44:22+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class DepartmentDtoMapperImpl implements DepartmentDtoMapper {

    @Autowired
    private ProfessorDtoMapper professorDtoMapper;

    @Override
    public Department mapToDepartment(DepartmentIncomingDto incomingDto) {
        if ( incomingDto == null ) {
            return null;
        }

        Department department = new Department();

        department.setName( incomingDto.getName() );
        if ( incomingDto.getId() != null ) {
            department.setId( incomingDto.getId() );
        }

        return department;
    }

    @Override
    public DepartmentOutGoingDto mapToDepartmentOutGoingDto(Department department) {
        if ( department == null ) {
            return null;
        }

        DepartmentOutGoingDto departmentOutGoingDto = new DepartmentOutGoingDto();

        departmentOutGoingDto.setId( department.getId() );
        departmentOutGoingDto.setName( department.getName() );

        return departmentOutGoingDto;
    }

    @Override
    public DepartmentWithProfessorsOutGoingDto mapToDepartmentWithProfessorsOutGoingDto(Department department) {
        if ( department == null ) {
            return null;
        }

        DepartmentWithProfessorsOutGoingDto departmentWithProfessorsOutGoingDto = new DepartmentWithProfessorsOutGoingDto();

        departmentWithProfessorsOutGoingDto.setId( department.getId() );
        departmentWithProfessorsOutGoingDto.setName( department.getName() );
        departmentWithProfessorsOutGoingDto.setProfessors( professorListToProfessorShortOutGoingDtoList( department.getProfessors() ) );

        return departmentWithProfessorsOutGoingDto;
    }

    protected List<ProfessorShortOutGoingDto> professorListToProfessorShortOutGoingDtoList(List<Professor> list) {
        if ( list == null ) {
            return null;
        }

        List<ProfessorShortOutGoingDto> list1 = new ArrayList<ProfessorShortOutGoingDto>( list.size() );
        for ( Professor professor : list ) {
            list1.add( professorDtoMapper.mapToProfessorShortOutGoingDto( professor ) );
        }

        return list1;
    }
}
