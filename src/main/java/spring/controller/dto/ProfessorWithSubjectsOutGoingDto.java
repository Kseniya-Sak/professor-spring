package spring.controller.dto;

import java.util.List;

public class ProfessorWithSubjectsOutGoingDto {
    private int id;
    private String name;
    private String surname;
    private DepartmentOutGoingDto department;
    private List<SubjectOutGoingDto> subjects;

    public ProfessorWithSubjectsOutGoingDto() {
    }

    public ProfessorWithSubjectsOutGoingDto(int id, String name, String surname, DepartmentOutGoingDto department, List<SubjectOutGoingDto> subjects) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.department = department;
        this.subjects = subjects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public DepartmentOutGoingDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentOutGoingDto department) {
        this.department = department;
    }

    public List<SubjectOutGoingDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectOutGoingDto> subjects) {
        this.subjects = subjects;
    }
}
