package org.project.LibraryAccountingSystem.Spring.Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person implements  Serializable {
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
    private List<Book> booksLibrarian;

    @OneToMany(mappedBy = "owner")
    private List<Book> booksPeople;

    @OneToMany(mappedBy = "ownerRequest")
    private List<BookRequest> bookRequestList;

    @OneToMany(mappedBy = "ownerPerson")
    private List<Review> reviewList;

    @OneToMany(mappedBy = "personOwner" )
    List<HistoryBook> historyBookList ;

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

    public List<BookRequest> getBookRequestList() {
        return bookRequestList;
    }

    public void setBookRequestList(List<BookRequest> bookRequestList) {
        this.bookRequestList = bookRequestList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
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

    public List<HistoryBook> getHistoryBookList() {
        return historyBookList;
    }

    public void setHistoryBookList(List<HistoryBook> historyBookList) {
        this.historyBookList = historyBookList;
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

    public List<Book> getBooksLibrarian() {
        return booksLibrarian;
    }

    public void setBooksLibrarian(List<Book> booksLibrarian) {
        this.booksLibrarian = booksLibrarian;
    }

    public List<Book> getBooksPeople() {
        return booksPeople;
    }

    public void setBooksPeople(List<Book> booksPeople) {
        this.booksPeople = booksPeople;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && age == person.age && Objects.equals(userName, person.userName) && Objects.equals(name, person.name) && Objects.equals(surname, person.surname) && Objects.equals(role, person.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, name, surname, age, role);
    }
}
