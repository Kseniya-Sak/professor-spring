package spring.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import spring.controller.dto.DepartmentIncomingDto;
import spring.controller.dto.DepartmentOutGoingDto;
import spring.controller.dto.DepartmentWithProfessorsOutGoingDto;
import spring.entity.Department;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ProfessorDtoMapper.class})
public interface DepartmentDtoMapper {
    Department mapToDepartment(DepartmentIncomingDto incomingDto);

    DepartmentOutGoingDto mapToDepartmentOutGoingDto(Department department);

    DepartmentWithProfessorsOutGoingDto mapToDepartmentWithProfessorsOutGoingDto(Department department);
}

