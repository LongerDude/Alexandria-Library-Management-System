package ControllerTests;

import com.LongerDude.LMS.model.Book;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;


import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

// Point explicitly to your main app class
@SpringBootTest(classes = com.LongerDude.LMS.LmsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql(scripts = "/sql/clean_books.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void shouldCreateAndRetrieveABook() {
        // 1. ARRANGE
        Book newBook = new Book("Harry Potter", "J.K. Rowling", 10);
        // 2. POST Request
        ResponseEntity<Void> createResponse = restTemplate.withBasicAuth("user","password")
                .postForEntity("/api/books", newBook, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI bookLocationUri = createResponse.getHeaders().getLocation();
        assertThat(bookLocationUri).isNotNull();

        // 3. Checking if Book exists in the database
        ResponseEntity<String> response = restTemplate.withBasicAuth("user","password").getForEntity(bookLocationUri, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // 4. Evaluating the fields.
        DocumentContext documentContext = JsonPath.parse(response.getBody());

        String title = documentContext.read("$.title");
        String author = documentContext.read("$.author");
        int amount = documentContext.read("$.amount");

        assertThat(title).isEqualTo(newBook.getTitle());
        assertThat(author).isEqualTo(newBook.getAuthor());
        assertThat(amount).isEqualTo(10);
    }
    @Test
    void shouldUpdateSpecifiedField() {
        Book bookUpdate = new Book(null,null, 20);
        HttpEntity<Book> request = new HttpEntity<>(bookUpdate);
        ResponseEntity<Void> response = restTemplate.withBasicAuth("user", "password").exchange("/api/books/1", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}

