package es.urjc.code.daw.library.book;

import org.springframework.stereotype.Component;

@Component
public class BookChecker {
    
    public boolean checkIfEmpty(Book book){
        return book.getTitle().isEmpty() || book.getDescription().isEmpty();
    }
}
