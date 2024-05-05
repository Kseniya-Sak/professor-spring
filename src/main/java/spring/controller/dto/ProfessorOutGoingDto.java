package spring.controller.dto;

public class ProfessorOutGoingDto {
    private int id;
    private String name;
    private String surname;
    private DepartmentOutGoingDto department;

    public ProfessorOutGoingDto() {
    }

    public ProfessorOutGoingDto(int id, String name, String surname, DepartmentOutGoingDto department) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.department = department;
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
}
