package org.project.LibraryAccountingSystem.Spring.Boot.services;

import org.project.LibraryAccountingSystem.Spring.Boot.models.Book;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Review;
import org.project.LibraryAccountingSystem.Spring.Boot.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookService bookService, PersonService personService) {
        this.reviewRepository = reviewRepository;
        this.bookService = bookService;
        this.personService = personService;
    }

    @Transactional
    public boolean save(Review review, Person personOwner, Book bookOwner) {
        review.setOwnerPerson(personOwner);
        review.setOwnerBook(bookOwner);

        List<Review> reviewList = reviewRepository.findAll();
        for (Review r : reviewList)
            if (r.getOwnerPerson().equals(personOwner) && r.getOwnerBook().equals(bookOwner))
                return false;

        reviewRepository.save(review);
        return true;
    }



    public List<Review> findAllForBook(int idBook) {
        return reviewRepository.findAllByOwnerBook(idBook);
    }
}
