package com.salvaroca.obrestdatajpa.controller;

import com.salvaroca.obrestdatajpa.entity.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    // Testing environment setup

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                  "title": "Sample Book",
                  "author": "Spring Test",
                  "pages": 100,
                  "price": 10.0,
                  "releaseDate": "2023-12-04",
                  "online": true
                }
                """;

        HttpEntity<String> postRequest = new HttpEntity<>(json, headers);

        testRestTemplate.exchange("/books", HttpMethod.POST, postRequest, Book.class);
    }

    @AfterEach
    void tearDown() {
        testRestTemplate.exchange("/books", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
    }

    @Test
    void getAllBooks() {
        ResponseEntity<Book[]> responseOk =
                testRestTemplate.getForEntity("/books", Book[].class);

        assertEquals(HttpStatus.OK, responseOk.getStatusCode());
    }

    @Test
    void getBookById() {
        ResponseEntity<Book> responseOk =
                testRestTemplate.getForEntity("/books/1", Book.class);

        assertEquals(HttpStatus.OK, responseOk.getStatusCode());

        ResponseEntity<Book> responseNotFound =
                testRestTemplate.getForEntity("/books/2", Book.class);

        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode());
    }

    @Test
    void registerBook() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String jsonOk = """
                {
                  "title": "Sample Book",
                  "author": "Spring Test New",
                  "pages": 100,
                  "price": 10.0,
                  "releaseDate": "2023-12-04",
                  "online": true
                }
                """;

        HttpEntity<String> requestOk = new HttpEntity<>(jsonOk, headers);
        ResponseEntity<Book> responseOk = testRestTemplate.exchange("/books", HttpMethod.POST, requestOk, Book.class);
        Book resultOk = responseOk.getBody();

        assertEquals(HttpStatus.OK,responseOk.getStatusCode());
        assertEquals(2L, resultOk.getId());
        assertEquals("Spring Test New", resultOk.getAuthor());

        String jsonBadRequest = """
                {
                  "id": 1
                  "title": "Sample Book",
                  "author": "Spring Test",
                  "pages": 100,
                  "price": 10.0,
                  "releaseDate": "2023-12-04",
                  "online": true
                }
                """;

        HttpEntity<String> requestBadRequest = new HttpEntity<>(jsonBadRequest, headers);
        ResponseEntity<Book> responseBadRequest = testRestTemplate.exchange("/books", HttpMethod.POST, requestBadRequest, Book.class);
        assertEquals(HttpStatus.BAD_REQUEST,responseBadRequest.getStatusCode());
    }

    @Test
    void updateBook() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String jsonOk = """
                {
                  "id": 1,
                  "title": "Sample Book",
                  "author": "Spring Test PUT",
                  "pages": 100,
                  "price": 10.0,
                  "releaseDate": "2023-12-04",
                  "online": true
                }
                """;

        HttpEntity<String> requestOk = new HttpEntity<>(jsonOk, headers);
        ResponseEntity<Book> responseOk = testRestTemplate.exchange("/books", HttpMethod.PUT, requestOk, Book.class);
        Book resultOk = responseOk.getBody();

        assertEquals(HttpStatus.OK,responseOk.getStatusCode());
        assertEquals(1L, resultOk.getId());
        assertEquals("Spring Test PUT", resultOk.getAuthor());

        String jsonBadRequest = """
                {
                  "title": "Sample Book",
                  "author": "Spring Test PUT",
                  "pages": 100,
                  "price": 10.0,
                  "releaseDate": "2023-12-04",
                  "online": true
                }
                """;

        HttpEntity<String> requestBadRequest = new HttpEntity<>(jsonBadRequest, headers);
        ResponseEntity<Book> responseBadRequest = testRestTemplate.exchange("/books", HttpMethod.PUT, requestBadRequest, Book.class);

        assertEquals(HttpStatus.BAD_REQUEST,responseBadRequest.getStatusCode());

        String jsonNotFound = """
                {
                  "id": 2,
                  "title": "Sample Book",
                  "author": "Spring Test PUT",
                  "pages": 100,
                  "price": 10.0,
                  "releaseDate": "2023-12-04",
                  "online": true
                }
                """;

        HttpEntity<String> requestNotFound = new HttpEntity<>(jsonNotFound, headers);
        ResponseEntity<Book> responseNotFound = testRestTemplate.exchange("/books", HttpMethod.PUT, requestNotFound, Book.class);

        assertEquals(HttpStatus.NOT_FOUND,responseNotFound.getStatusCode());
    }

    @Test
    void deleteAllBooks() {
        ResponseEntity<Book> responseNoContent = testRestTemplate.exchange("/books", HttpMethod.DELETE, HttpEntity.EMPTY, Book.class);

        assertEquals(HttpStatus.NO_CONTENT, responseNoContent.getStatusCode());
    }

    @Test
    void deleteBook() {
        ResponseEntity<Book> responseNoContent = testRestTemplate.exchange("/books/1", HttpMethod.DELETE, HttpEntity.EMPTY, Book.class);

        assertEquals(HttpStatus.NO_CONTENT, responseNoContent.getStatusCode());

        ResponseEntity<Book> responseNotFound = testRestTemplate.exchange("/books/1", HttpMethod.DELETE, HttpEntity.EMPTY, Book.class);

        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode());
    }
}