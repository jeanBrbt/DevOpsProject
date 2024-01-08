package org.grostarin.springboot.demorest.repositories;

import java.util.List;

import org.grostarin.springboot.demorest.domain.BannedBook;
import org.springframework.data.repository.CrudRepository;

public interface BannedBookRepository extends CrudRepository<BannedBook, Long> {
    List<BannedBook> findByTitle(String title);
    BannedBook findByTitleAndAuthor(String title, String author);

}
