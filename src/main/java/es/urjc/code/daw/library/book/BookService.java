package es.urjc.code.daw.library.book;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.urjc.code.daw.library.notification.NotificationService;

/* Este servicio se usará para incluir la funcionalidad que sea 
 * usada desde el BookRestController y el BookWebController
 */
@Service
public class BookService {

	private BookRepository repository;
	private NotificationService notificationService;
	private BookChecker bookChecker;

	public BookService(BookRepository repository, NotificationService notificationService, BookChecker bookChecker){
		this.repository = repository;
		this.notificationService = notificationService;
		this.bookChecker = bookChecker;
	}

	public Optional<Book> findOne(long id) {
		return repository.findById(id);
	}
	
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public List<Book> findAll() {
		return repository.findAll();
	}

	public Book save(Book book) {

		if(bookChecker.checkIfEmpty(book))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title and description can't be blank");

		var newBook = repository.save(book);
		notificationService.notify("Book Event: book with title="+newBook.getTitle()+" was created");
		return newBook;
		
	}

	public Book edit(Book book) {

		if(bookChecker.checkIfEmpty(book))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title and description can't be blank");

		var newBook = repository.save(book);
		notificationService.notify("Book Event: book with title="+newBook.getTitle()+" was modified");
		return newBook;
	}

	public void delete(long id) {
		repository.deleteById(id);
		notificationService.notify("Book Event: book with id="+id+" was deleted");
	}
}
