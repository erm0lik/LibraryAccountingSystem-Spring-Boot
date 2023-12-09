package org.project.LibraryAccountingSystem.Spring.Boot.repositories;

import org.project.LibraryAccountingSystem.Spring.Boot.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review , Integer> {
    @Query(value = "SELECT * FROM review WHERE id_book=:idBook" , nativeQuery = true)
    List<Review> findAllByOwnerBook(@Param("idBook") int idBook);
}
