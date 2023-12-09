package org.project.LibraryAccountingSystem.Spring.Boot.models;

import jakarta.persistence.*;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id ;
    @Column(name = "stars")
    private int stars ;
    @Column(name = "text_review")
    private String textReview;
    @ManyToOne
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    private Person ownerPerson ;
    @ManyToOne
    @JoinColumn(name = "id_book" , referencedColumnName = "books_id")
    private Book ownerBook ;

    public Review() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getTextReview() {
        return textReview;
    }

    public void setTextReview(String textReview) {
        this.textReview = textReview;
    }

    public Person getOwnerPerson() {
        return ownerPerson;
    }

    public void setOwnerPerson(Person ownerPerson) {
        this.ownerPerson = ownerPerson;
    }

    public Book getOwnerBook() {
        return ownerBook;
    }

    public void setOwnerBook(Book ownerBook) {
        this.ownerBook = ownerBook;
    }
}
