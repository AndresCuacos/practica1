package es.urjc.code.daw.library.book;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class DTOBook implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id = null;
	
	private String title;
	
	@Column(length = 50000)
	private String description;

	public DTOBook() {
		
		super();
	}

	public DTOBook(String nombre, String description) {
		super();
		this.title = nombre;
		this.description = description;
	}
public DTOBook(Book book) {
		
	this.title = book.getTitle();
	this.description = book.getDescription();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


}