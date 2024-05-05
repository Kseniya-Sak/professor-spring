package spring.service;

import spring.controller.dto.SubjectIncomingDto;
import spring.controller.dto.SubjectOutGoingDto;
import spring.controller.dto.SubjectWithProfessorsOutGoingDto;

import java.util.List;

public interface SubjectService {
    SubjectOutGoingDto save(SubjectIncomingDto subjectIncomingDto);

    void update(SubjectIncomingDto subjectIncomingDto);

    SubjectWithProfessorsOutGoingDto findById(Integer id);

    List<SubjectOutGoingDto> findAll();

    void deleteById(Integer id);
}
