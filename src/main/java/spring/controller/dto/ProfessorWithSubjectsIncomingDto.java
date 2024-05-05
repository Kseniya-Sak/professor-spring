package spring.controller.dto;

import java.util.List;

public class ProfessorWithSubjectsIncomingDto {
    private int id;
    private String name;
    private String surname;
    private DepartmentIncomingDto department;
    private List<SubjectIncomingDto> subjects;

    public ProfessorWithSubjectsIncomingDto() {
    }

    public ProfessorWithSubjectsIncomingDto(int id, String name, String surname, DepartmentIncomingDto department, List<SubjectIncomingDto> subjects) {
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

    public DepartmentIncomingDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentIncomingDto department) {
        this.department = department;
    }

    public List<SubjectIncomingDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectIncomingDto> subjects) {
        this.subjects = subjects;
    }
}
