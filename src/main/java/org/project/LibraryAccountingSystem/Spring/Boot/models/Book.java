package org.project.LibraryAccountingSystem.Spring.Boot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "books_id")
    private int id;

    @NotEmpty(message = "Enter the name")
    @Size(min = 1, max = 200, message = "Enter the valid name")
    @Column(name = "name")
    private String name;
    @NotEmpty(message = "Enter the author")
    @Size(min = 3, max = 200, message = "Enter the valid author")
    @Column(name = "author")
    private String author;
    @Min(value = 0, message = "Incorrect year(Correct:0-2023)")
    @Max(value = 2023, message = "Incorrect year(Correct:0-2023)")
    @NotNull(message = "Enter the year of publication")
    @Column(name = "yearOfPublication")
    private int yearOfPublication;
    @Column(name = "date_taken")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTaken;

    @ManyToOne
    @JoinColumn(name = "people_id", referencedColumnName = "id")
    private Person owner;

    @ManyToOne
    @JoinColumn(name = "person_id" , referencedColumnName = "id")
    private Person librarian;

    @OneToMany(mappedBy = "book")
    private List<BookRequest> bookRequestList ;

    @OneToMany(mappedBy = "ownerBook")
    private List<Review> reviewList;
    @Transient
    private boolean overdue;

    public Book() {
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public Person getOwner() {
        return owner;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Person getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Person librarian) {
        this.librarian = librarian;
    }

    public List<BookRequest> getBookRequestList() {
        return bookRequestList;
    }

    public void setBookRequestList(List<BookRequest> bookRequestList) {
        this.bookRequestList = bookRequestList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && yearOfPublication == book.yearOfPublication && name.equals(book.name) && author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, yearOfPublication);
    }
}
