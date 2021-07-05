package es.urjc.code.daw.library;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.urjc.code.daw.library.book.Book;
import es.urjc.code.daw.library.book.BookChecker;
import es.urjc.code.daw.library.book.BookRepository;
import es.urjc.code.daw.library.book.BookService;
import es.urjc.code.daw.library.notification.NotificationService;


 class TestBookService {
	
    private BookService bookS;
    private NotificationService notS;
    private BookRepository bookR;
    private BookChecker bookC;
    
	@BeforeEach
	public void setUp() {

		bookR = mock(BookRepository.class);
        notS = mock(NotificationService.class);
        bookC = mock(BookChecker.class);
        bookS= new BookService(bookR, notS,bookC);
	}

	@Test 
	 void editBookCorrectly() {
		
		//Given 
		Book bookA = new Book("Libro1", "Primer libro");
		when(bookR.save(bookA)).thenReturn(bookA);
		
		//When
		bookA.setTitle("Libro2");
		bookA.setDescription("El mejor libro"); 
		bookS.edit(bookA);
		
		//Then
		verify(bookR).save(bookA);
		verify(notS).notify("Book Event: book with title="+bookA.getTitle()+" was modified");;
	}

	@Test
	 void editBookWrongly() {
		
		//Given 
		Book bookA = new Book("Libro", "fefef");
		when(bookR.save(bookA)).thenReturn(bookA);
	//	bookA=bookS.save(bookA);		
		//When
		
		bookA.setDescription("");
		bookS.edit(bookA);
		
		//Then
		
		verify(bookR, never()).save(bookA);
		verify(notS, times(0)).notify("Book Event: book with title="+bookA.getTitle()+" was modified");
		//verify(bookC).checkIfEmpty(bookA);
		//assertTrue(bookC.checkIfEmpty(bookA));
	}
}