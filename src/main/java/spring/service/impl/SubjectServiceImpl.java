package spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.controller.dto.SubjectIncomingDto;
import spring.controller.dto.SubjectOutGoingDto;
import spring.controller.dto.SubjectWithProfessorsOutGoingDto;
import spring.controller.mapper.SubjectDtoMapper;
import spring.entity.Subject;
import spring.exception.NotFoundException;
import spring.exception.RepositoryException;
import spring.repository.SubjectRepository;
import spring.service.SubjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static spring.service.ParameterHandler.throwExceptionIfParameterNull;

@Transactional(readOnly = true)
@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectDtoMapper mapper;
    private final SubjectRepository repository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository repository,  SubjectDtoMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    @Transactional
    public SubjectOutGoingDto save(SubjectIncomingDto subjectIncomingDto) {
        throwExceptionIfParameterNull(subjectIncomingDto, "The subject must not be null");
        throwExceptionIfParameterNull(subjectIncomingDto.getName(), "The subject's name must not be null");
        throwExceptionIfParameterNull(subjectIncomingDto.getValueOfHours(),
                "The subject's value of hours must be greater than 0");

        if (exitsByName(subjectIncomingDto.getName())) {
            throw new RepositoryException("A subject named " + subjectIncomingDto.getName() + " already exists");
        }

        Subject savedSubject = repository.save(mapper.map(subjectIncomingDto));
        return mapper.map(savedSubject);
    }

    @Override
    @Transactional
    public void update(SubjectIncomingDto subjectIncomingDto) {
        throwExceptionIfParameterNull(subjectIncomingDto, "The subject must not be null");
        throwExceptionIfParameterNull(subjectIncomingDto.getName(), "The subject's name must not be null");
        throwExceptionIfParameterNull(subjectIncomingDto.getValueOfHours(),
                "The subject's value of hours must be greater than 0");
        checkExistById(subjectIncomingDto.getId());

        repository.save(mapper.map(subjectIncomingDto));
    }

    @Override
    public SubjectWithProfessorsOutGoingDto findById(Integer id) {
        throwExceptionIfParameterNull(id, "The ID must not be null");

        Optional<Subject> optional = repository.findByIdWithProfessor(id);
        if (optional.isPresent()) {
            return mapper.mapToWithProfessorsOutGoingDto(optional.get());
        } else {
            throw new NotFoundException("Subject with ID = " + id + " does not exist in the database");
        }
    }

    @Override
    public List<SubjectOutGoingDto> findAll() {
        List<Subject> subjects = repository.findAll();
        List<SubjectOutGoingDto> subjectOutGoingDtos = new ArrayList<>();
        for (Subject subject : subjects) {
            subjectOutGoingDtos.add(mapper.map(subject));
        }
        return subjectOutGoingDtos;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        throwExceptionIfParameterNull(id, "The ID must not be null");
        checkExistById(id);

        repository.deleteById(id);
    }

    private boolean exitsByName(String name) {
        return repository.existsByName(name);
    }

    private void checkExistById(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Subject with ID = " + id + " does not exist in the database");
        }
    }
}
