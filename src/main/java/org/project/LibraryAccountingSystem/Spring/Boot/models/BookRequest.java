package org.project.LibraryAccountingSystem.Spring.Boot.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "book_request")
public class BookRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "asCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date ;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person ownerRequest;

    @ManyToOne
    @JoinColumn(name = "book_id" , referencedColumnName = "books_id")
    private Book book;

    public BookRequest() {
    }

    public BookRequest(Person ownerRequest, Book book) {
        this.ownerRequest = ownerRequest;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Person getOwner() {
        return ownerRequest;
    }

    public void setOwner(Person ownerRequest) {
        this.ownerRequest = ownerRequest;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRequest that = (BookRequest) o;
        return this.getOwner().getId() == that.getOwner().getId() &&
                this.getBook().getBooks_id() == that.getBook().getBooks_id();
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerRequest, book);
    }
}
