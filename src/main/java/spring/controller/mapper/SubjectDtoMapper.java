package spring.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import spring.controller.dto.SubjectIncomingDto;
import spring.controller.dto.SubjectOutGoingDto;
import spring.controller.dto.SubjectWithProfessorsOutGoingDto;
import spring.entity.Subject;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ProfessorDtoMapper.class})
public interface SubjectDtoMapper {

    Subject map(SubjectIncomingDto incomingDto);
    SubjectOutGoingDto map(Subject subject);

    SubjectWithProfessorsOutGoingDto mapToWithProfessorsOutGoingDto(Subject subject);
}
