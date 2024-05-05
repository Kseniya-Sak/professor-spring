package spring.controller.dto;

public class SubjectIncomingDto {
    private int id;
    private String name;
    private int valueOfHours;

    public SubjectIncomingDto() {
    }

    public SubjectIncomingDto(String name, int valueOfHours) {
        this.name = name;
        this.valueOfHours = valueOfHours;
    }

    public SubjectIncomingDto(int id, String name, int valueOfHours) {
        this.id = id;
        this.name = name;
        this.valueOfHours = valueOfHours;
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
}
