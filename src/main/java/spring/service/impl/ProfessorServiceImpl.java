package spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.controller.dto.ProfessorOutGoingDto;
import spring.controller.dto.ProfessorWithSubjectsIncomingDto;
import spring.controller.dto.ProfessorWithSubjectsOutGoingDto;
import spring.controller.mapper.ProfessorDtoMapper;
import spring.entity.Professor;
import spring.exception.NotFoundException;
import spring.repository.ProfessorRepository;
import spring.service.ProfessorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static spring.service.ParameterHandler.throwExceptionIfParameterNull;

@Transactional(readOnly = true)
@Service
public class ProfessorServiceImpl implements ProfessorService {
    private final ProfessorDtoMapper mapper;
    private final ProfessorRepository repository;

    @Autowired
    public ProfessorServiceImpl(ProfessorRepository repository, ProfessorDtoMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    @Transactional
    public ProfessorWithSubjectsOutGoingDto save(ProfessorWithSubjectsIncomingDto professorIncomingDto) {
        throwExceptionIfParameterNull(professorIncomingDto, "The professor must not be null");
        throwExceptionIfParameterNull(professorIncomingDto.getName(), "The professor's name must not be null");
        throwExceptionIfParameterNull(professorIncomingDto.getSurname(),
                "The professor's surname must not be null");
        Professor savedProfessor = repository.save(mapper.mapToProfessorWithSubjects(professorIncomingDto));

        return mapper.mapToProfessorWithSubjectsOutGoingDto(savedProfessor);
    }

    @Override
    @Transactional
    public void update(ProfessorWithSubjectsIncomingDto professorIncomingDto) {
        throwExceptionIfParameterNull(professorIncomingDto,
                "The professor must not be null");
        throwExceptionIfParameterNull(professorIncomingDto.getName(),
                "The professor's name must not be null");
        throwExceptionIfParameterNull(professorIncomingDto.getSurname(),
                "The professor's surname must not be null");

        checkExistById(professorIncomingDto.getId());

        repository.save(mapper.mapToProfessorWithSubjects(professorIncomingDto));
    }

    @Override
    public ProfessorWithSubjectsOutGoingDto findById(Integer id) {
        throwExceptionIfParameterNull(id, "The ID must not be null");

        Optional<Professor> optional = repository.findByIdWithSubject(id);
        if (optional.isPresent()) {
            return mapper.mapToProfessorWithSubjectsOutGoingDto(optional.get());
        } else {
            throw new NotFoundException("Professor with ID = " + id + " does not exist in the database");
        }
    }

    @Override
    public List<ProfessorOutGoingDto> findAll() {
        List<Professor> professors = repository.findAll();
        List<ProfessorOutGoingDto> professorOutGoingDtos = new ArrayList<>();
        for (Professor professor : professors) {
            professorOutGoingDtos.add(mapper.map(professor));
        }
        return professorOutGoingDtos;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        throwExceptionIfParameterNull(id, "The ID must not be null");
        checkExistById(id);

        repository.deleteById(id);
    }

    private void checkExistById(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Professor with ID = " + id + " does not exist in the database");
        }
    }
}
