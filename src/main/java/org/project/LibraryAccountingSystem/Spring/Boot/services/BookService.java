package org.project.LibraryAccountingSystem.Spring.Boot.services;


import org.project.LibraryAccountingSystem.Spring.Boot.models.Book;
import org.project.LibraryAccountingSystem.Spring.Boot.models.Person;
import org.project.LibraryAccountingSystem.Spring.Boot.repositories.BookRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class BookService {
    private final BookRepositories bookRepositories;

    private final PersonService personService;
    @Value("${file.upload.path}")
    private String pdfPath;

    @Autowired
    public BookService(BookRepositories bookRepositories, PersonService personService) {
        this.bookRepositories = bookRepositories;


        this.personService = personService;
    }

    @Transactional
    public boolean inUse(int id) {
        return bookRepositories.findById(id).orElse(new Book()).getOwner() != null;
    }

    public List<Book> findAll() {
        return bookRepositories.findAll();
    }

    //SELECT * FROM books LIMIT size OFFSET (page-1) * size
    public List<Book> findAll(int page, int size) {
        return bookRepositories.findAll(PageRequest.of(page, size)).getContent();
    }

    //SELECT * FROM books ORDER BY name_column ( строка яка приходить в параметрах )
    public List<Book> findAll(String sort) {
        return bookRepositories.findAll(Sort.by(sort));
    }
    //SELECT * FROM books ORDER BY name_column
    //LIMIT size OFFSET (page-1) * size
    public List<Book> findAll(int page, int size, String sort) {
        return bookRepositories.findAll(PageRequest.of(page, size, Sort.by(sort))).getContent();
    }
    //SELECT * books WHERE books_id = id
    public Book findById(int id) {
        return bookRepositories.findById(id).orElse(null);
    }

    @Transactional
    public void saveBook(Book book, MultipartFile multipartFile) {
        try {
            byte[] pdfBytes = multipartFile.getBytes();
            // Указываем путь к папке, где нужно сохранить файл
            Path uploadPath = Paths.get(pdfPath);


            // Генерируем уникальное имя файла (или используем оригинальное, если требуется)
            String fileName = String.valueOf(bookRepositories.save(book).getId() + ".pdf");
            Path filePath = uploadPath.resolve(fileName);

            // Сохраняем файл
            Files.write(filePath, pdfBytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public void editBook(Book book, int id) {
        book.setId(id);
        bookRepositories.save(book);
    }

    @Transactional
    public void deleteBook(int id) {

        bookRepositories.deleteById(id);
        try {
            Path delete = Paths.get(pdfPath + id + ".pdf");
            Files.delete(delete);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public void setPeopleForBook(int people_id, int books_id, int person_id) {
    bookRepositories.set_people_for_book(people_id ,person_id , books_id);
    }

    @Transactional
    public void deletePeopleForBook(int book_id) {
        bookRepositories.deletePeopleForBook(book_id);
    }

    public Book findByStartingWith(String start) {
        if (start.isEmpty()) return null;

        return bookRepositories.findByNameStartingWithIgnoreCase(start);
    }

    public List<Book> findFreeBook(int personId) {

        return bookRepositories.findFreeBooksForPerson(personId);
//        List<Book> listBook =  bookRepositories.findByOwnerIsNull();
//        listBook.removeIf(book -> book.getBookRequestList().contains(new BookRequest(person, book)));
//        return listBook;
    }
    public Double getAverageRatingForBook(int idBook){
        return bookRepositories.getAverageRatingForBook(idBook);
    }


}
