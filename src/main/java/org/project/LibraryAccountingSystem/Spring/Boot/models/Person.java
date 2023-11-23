package org.project.LibraryAccountingSystem.Spring.Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id ;
    @Column(name = "username")
    @Size(min = 2 , max = 100 , message = "Enter valid user name")
    private String userName;
    @Column(name = "name")
    @Size(min = 2 , max = 50 , message = "Enter valid name")
    private String name ;
    @Column(name = "surname")
    @Size(min = 2 , max = 50 , message = "Enter valid surname")
    private String surname ;
    @Max(value = 120 , message = "Enter valid age(<100)")
    @Min(value = 18 , message = "Enter valid age(>18)")
    private int age ;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role ;

    @OneToMany(mappedBy = "librarian")
    private List<Book> books;


    @Transient
    private boolean isAuth ;


    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }
}
