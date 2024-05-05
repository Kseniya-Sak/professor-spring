package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.controller.dto.ExceptionDto;
import spring.controller.dto.ProfessorOutGoingDto;
import spring.controller.dto.ProfessorWithSubjectsIncomingDto;
import spring.controller.dto.ProfessorWithSubjectsOutGoingDto;
import spring.exception.NotFoundException;
import spring.exception.RepositoryException;
import spring.service.ProfessorService;

import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private final ProfessorService service;

    @Autowired
    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ProfessorWithSubjectsOutGoingDto getProfessorById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @GetMapping
    public List<ProfessorOutGoingDto> getProfessors() {
        return service.findAll();
    }

    @PostMapping
    public ProfessorWithSubjectsOutGoingDto createProfessor(@RequestBody ProfessorWithSubjectsIncomingDto professor) {
        return  service.save(professor);
    }

    @PutMapping("/{id}")
    public void updateProfessor(@PathVariable("id") int id, @RequestBody ProfessorWithSubjectsIncomingDto professor) {
        professor.setId(id);
        service.update(professor);
    }

    @DeleteMapping("/{id}")
    public void deleteProfessor(@PathVariable("id") int id) {
        service.deleteById(id);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ExceptionDto> catchNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RepositoryException.class})
    public ResponseEntity<ExceptionDto> catchRepositoryException(RepositoryException e) {
        return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

