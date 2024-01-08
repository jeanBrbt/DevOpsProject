package org.grostarin.springboot.demorest.services;

import org.grostarin.springboot.demorest.domain.BannedBook;
import org.grostarin.springboot.demorest.dto.BannedBookSearch;
import org.grostarin.springboot.demorest.exceptions.BannedBookIdMismatchException;
import org.grostarin.springboot.demorest.exceptions.BannedBookNotFoundException;
import org.grostarin.springboot.demorest.exceptions.BannedBookException;

import org.grostarin.springboot.demorest.repositories.BannedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BannedBookServices {    

    @Autowired
    private BannedBookRepository bannedBookRepository;
    
    public Iterable<BannedBook> findAll(BannedBookSearch bannedBookSearchDTO) {
        if(bannedBookSearchDTO!=null && StringUtils.hasText(bannedBookSearchDTO.title())) {
            return bannedBookRepository.findByTitle(bannedBookSearchDTO.title());  
        }
        return bannedBookRepository.findAll();
    }

    public boolean exist(String title, String authors) {
        BannedBook bannedBook = bannedBookRepository.findByTitleAndAuthor(title, authors);
        return bannedBook != null;
    }

    public BannedBook findOne(long id) {
        return bannedBookRepository.findById(id)
          .orElseThrow(BannedBookException::new);
    }

    public BannedBook create(BannedBook bannedBook) {
        BannedBook bannedBook1 = bannedBookRepository.save(bannedBook);
        return bannedBook1;
    }

    public void delete(long id) {
        bannedBookRepository.findById(id)
          .orElseThrow(BannedBookNotFoundException::new);
        bannedBookRepository.deleteById(id);
    }

    public BannedBook updateBannedBook(BannedBook bannedBook, long id) {
        if (bannedBook.getId() != id) {
            throw new BannedBookIdMismatchException();
        }
        bannedBookRepository.findById(id)
          .orElseThrow(BannedBookNotFoundException::new);
        return bannedBookRepository.save(bannedBook);
    }
}
