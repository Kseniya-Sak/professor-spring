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
import spring.controller.dto.SubjectIncomingDto;
import spring.controller.dto.SubjectOutGoingDto;
import spring.controller.dto.SubjectWithProfessorsOutGoingDto;
import spring.exception.NotFoundException;
import spring.exception.RepositoryException;
import spring.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService service;

    @Autowired
    public SubjectController(SubjectService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public SubjectWithProfessorsOutGoingDto getSubjectById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @GetMapping
    public List<SubjectOutGoingDto> getSubjects() {
        return service.findAll();
    }

    @PostMapping
    public SubjectOutGoingDto createSubject(@RequestBody SubjectIncomingDto subjectIncomingDto) {
        return  service.save(subjectIncomingDto);
    }

    @PutMapping("/{id}")
    public void updateSubject(@PathVariable("id") int id, @RequestBody SubjectIncomingDto subjectIncomingDto) {
        subjectIncomingDto.setId(id);
        service.update(subjectIncomingDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable("id") int id) {
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

