package spring.controller.dto;

import java.util.List;

public class SubjectWithProfessorsOutGoingDto {
    private int id;
    private String name;
    private int valueOfHours;
    private List<ProfessorOutGoingDto> professors;

    public SubjectWithProfessorsOutGoingDto() {
    }

    public SubjectWithProfessorsOutGoingDto(int id, String name, int valueOfHours, List<ProfessorOutGoingDto> professors) {
        this.id = id;
        this.name = name;
        this.valueOfHours = valueOfHours;
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

    public int getValueOfHours() {
        return valueOfHours;
    }

    public void setValueOfHours(int valueOfHours) {
        this.valueOfHours = valueOfHours;
    }

    public List<ProfessorOutGoingDto> getProfessors() {
        return professors;
    }

    public void setProfessors(List<ProfessorOutGoingDto> professors) {
        this.professors = professors;
    }
}
