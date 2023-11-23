package org.project.LibraryAccountingSystem.Spring.Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "people")
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_id")
    private int people_id;
    @NotEmpty(message = "Enter the name")
    @Size(min = 3, max = 200, message = "Enter the valid name")
    @Column(name = "fullName")
    private String fullName;
    @Min(value = 0, message = "Incorrect birth year(Correct:1900-2023)")
    @Max(value = 2023, message = "Incorrect birth year(Correct:1900-2023)")
    @Column(name = "birthYear")
    private int birthYear;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public People() {
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getPeople_id() {
        return people_id;
    }

    public void setPeople_id(int people_id) {
        this.people_id = people_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
