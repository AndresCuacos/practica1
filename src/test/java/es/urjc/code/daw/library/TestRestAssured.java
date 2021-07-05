package es.urjc.code.daw.library;

	import static io.restassured.RestAssured.given;
	import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.hasItem;
import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.boot.web.server.LocalServerPort;

	import com.fasterxml.jackson.core.JsonProcessingException;
	import com.fasterxml.jackson.databind.ObjectMapper;

	import es.urjc.code.daw.library.book.Book;
	import io.restassured.RestAssured;
	import io.restassured.http.ContentType;
	

	@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	 class TestRestAssured {
		
		@LocalServerPort
	    int port;
		
		@BeforeEach
	    public void setUp() {
	        RestAssured.port = port;
	    }
		@Autowired
	    private ObjectMapper objectMapper;
		
		@Test
	    void bookAddedEdittedAndGetAll() throws JsonProcessingException {
			//given 
			Book book = new Book("libro1","Primer libro creado");
			Book bookA = 
		            given()
		                .request()
		                    .body(objectMapper.writeValueAsString(book))
		                    .contentType(ContentType.JSON)
		            .when()
		                .post("/api/books/")
		            .then()
		                .assertThat()
		                .statusCode(201)
		                .extract().as(Book.class); 
			//When
			Book bookNew = new Book("libro1v2","Primer libro creado");
					 given()
					.request()
                    .body(objectMapper.writeValueAsString(bookNew))
                    .contentType(ContentType.JSON)
                    .when()
                    .put("/api/books/{id}",bookA.getId());
				
			//Then
				when()
					.get("/api/books/").
					then().assertThat()
						.body( "title",hasItem("libro1v2"));
		}
	}
