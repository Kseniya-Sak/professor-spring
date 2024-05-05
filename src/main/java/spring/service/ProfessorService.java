package spring.service;

import spring.controller.dto.ProfessorOutGoingDto;
import spring.controller.dto.ProfessorWithSubjectsIncomingDto;
import spring.controller.dto.ProfessorWithSubjectsOutGoingDto;

import java.util.List;

public interface ProfessorService {
    ProfessorWithSubjectsOutGoingDto save(ProfessorWithSubjectsIncomingDto professorIncomingDto);
    void update(ProfessorWithSubjectsIncomingDto professorIncomingDto);
    ProfessorWithSubjectsOutGoingDto findById(Integer id);
    List<ProfessorOutGoingDto> findAll();
    void deleteById(Integer id);
}
