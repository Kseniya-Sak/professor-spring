package spring.entity;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "value_of_hours")
    private int valueOfHours;

    @ManyToMany(mappedBy = "subjects")
    private List<Professor> professors = new ArrayList<>();

    public Subject() {
    }

    public Subject(String name, int valueOfHours) {
        this.name = name;
        this.valueOfHours = valueOfHours;
    }

    @PreRemove
    private void removeSubjectsFromProfessors() {
        for (Professor professor : professors) {
            professor.getSubjects().remove(this);
        }
    }

    public Subject(int id, String name, int valueOfHours) {
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

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }
}

