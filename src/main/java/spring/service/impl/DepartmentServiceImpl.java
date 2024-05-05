package spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.controller.dto.DepartmentIncomingDto;
import spring.controller.dto.DepartmentOutGoingDto;
import spring.controller.dto.DepartmentWithProfessorsOutGoingDto;
import spring.controller.mapper.DepartmentDtoMapper;
import spring.entity.Department;
import spring.exception.NotFoundException;
import spring.exception.RepositoryException;
import spring.repository.DepartmentRepository;
import spring.service.DepartmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static spring.service.ParameterHandler.throwExceptionIfParameterNull;

@Transactional(readOnly = true)
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentDtoMapper mapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentDtoMapper mapper) {
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
    }

    @Override
    public List<DepartmentOutGoingDto> findAll() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentOutGoingDto> departmentOutGoingDtos = new ArrayList<>();
        for (Department department : departments) {
            departmentOutGoingDtos.add(mapper.mapToDepartmentOutGoingDto(department));
        }

        return departmentOutGoingDtos;
    }

    @Override
    public DepartmentWithProfessorsOutGoingDto findById(Integer id) {
        throwExceptionIfParameterNull(id, "The ID must not be null");

        Optional<Department> optional = departmentRepository.findByIdWithProfessor(id);
        if (optional.isPresent()) {
            return mapper.mapToDepartmentWithProfessorsOutGoingDto(optional.get());
        } else {
            throw new NotFoundException("Department with ID = " + id + " does not exist in the database");
        }
    }

    @Transactional
    @Override
    public DepartmentOutGoingDto save(DepartmentIncomingDto departmentIncomingDto) {
        throwExceptionIfParameterNull(departmentIncomingDto,
                "The department must not be null");
        throwExceptionIfParameterNull(departmentIncomingDto.getName(),
                "The department's name must not be null");

        if (exitsByName(departmentIncomingDto.getName())) {
            throw new RepositoryException("A department named " +
                    departmentIncomingDto.getName() +
                    " already exists");
        }

        Department savedDepartment = departmentRepository.save(mapper.mapToDepartment(departmentIncomingDto));
        return mapper.mapToDepartmentOutGoingDto(savedDepartment);
    }

    @Transactional
    @Override
    public void update(DepartmentIncomingDto departmentIncomingDto) {
        throwExceptionIfParameterNull(departmentIncomingDto,
                "The department must not be null");
        throwExceptionIfParameterNull(departmentIncomingDto.getName(),
                "The department's name must not be null");

        checkExistById(departmentIncomingDto.getId());

        Department department = mapper.mapToDepartment(departmentIncomingDto);
        departmentRepository.save(department);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        throwExceptionIfParameterNull(id, "The ID must not be null");

        checkExistById(id);

        departmentRepository.deleteById(id);
    }

    private boolean exitsByName(String name) {
        return departmentRepository.existsByName(name);
    }

    private void checkExistById(Integer id) {
        if (!departmentRepository.existsById(id)) {
            throw new NotFoundException("Department with ID = " + id + " does not exist in the database");
        }
    }
}
