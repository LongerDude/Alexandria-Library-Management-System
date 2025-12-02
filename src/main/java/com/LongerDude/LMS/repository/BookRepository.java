package com.LongerDude.LMS.repository;

import com.LongerDude.LMS.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    //IoC will take care of this at runtime?

}
