package spring.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import spring.controller.dto.ProfessorOutGoingDto;
import spring.controller.dto.ProfessorShortOutGoingDto;
import spring.controller.dto.ProfessorWithSubjectsIncomingDto;
import spring.controller.dto.ProfessorWithSubjectsOutGoingDto;
import spring.entity.Professor;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {DepartmentDtoMapper.class, SubjectDtoMapper.class})
public interface ProfessorDtoMapper {
    ProfessorOutGoingDto map(Professor professor);

    ProfessorShortOutGoingDto mapToProfessorShortOutGoingDto(Professor professor);

    ProfessorWithSubjectsOutGoingDto mapToProfessorWithSubjectsOutGoingDto(Professor professor);

    Professor mapToProfessorWithSubjects(ProfessorWithSubjectsIncomingDto incomingDto);
}
