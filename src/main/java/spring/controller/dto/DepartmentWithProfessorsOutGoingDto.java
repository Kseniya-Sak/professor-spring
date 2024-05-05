package spring.controller.dto;

import java.util.List;

public class DepartmentWithProfessorsOutGoingDto {
    private int id;
    private String name;
    private List<ProfessorShortOutGoingDto> professors;

    public DepartmentWithProfessorsOutGoingDto() {
    }

    public DepartmentWithProfessorsOutGoingDto(int id, String name, List<ProfessorShortOutGoingDto> professors) {
        this.id = id;
        this.name = name;
        this.professors = professors;
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

    public List<ProfessorShortOutGoingDto> getProfessors() {
        return professors;
    }

    public void setProfessors(List<ProfessorShortOutGoingDto> professors) {
        this.professors = professors;
    }
}
