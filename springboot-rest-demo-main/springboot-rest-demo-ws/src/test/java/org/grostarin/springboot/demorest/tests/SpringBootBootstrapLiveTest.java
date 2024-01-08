package org.grostarin.springboot.demorest.tests;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.grostarin.springboot.demorest.domain.BannedBook;
import org.grostarin.springboot.demorest.domain.Book;
import org.grostarin.springboot.demorest.services.BookServices;
import org.grostarin.springboot.demorest.services.BannedBookServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBootBootstrapLiveTest {

    @LocalServerPort
    private int port;

    @Autowired
    private BookServices bookServices;
       
    private String getApiRoot() {
        return "http://localhost:"+port+"/api/books";
    }
    private String getApiBannedBookRoot() {
        return "http://localhost:"+port+"/api/BannedBooks";
    }

    @Test
    public void whenGetAllBooks_thenOK() {
        final Response response = RestAssured.get(getApiRoot());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetBooksByTitle_thenOK() {
        final Book book = createRandomBook();
        createBookAsUri(book);

        final Response response = RestAssured.get(getApiRoot() + "?title=" + book.getTitle());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
            .size() > 0);
    }

    @Test
    public void whenGetCreatedBookById_thenOK() {
        final Book book = createRandomBook();
        final String location = createBookAsUri(book);

        final Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(book.getTitle(), response.jsonPath()
            .get("title"));
    }

    @Test
    public void whenGetNotExistBookById_thenNotFound() {
        final Response response = RestAssured.get(getApiRoot() + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // POST
    @Test
    public void whenCreateNewBook_thenCreated() {
        final Book book = createRandomBook();

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .post(getApiRoot());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidBook_thenError() {
        final Book book = createRandomBook();
        book.setAuthor(null);

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .post(getApiRoot());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedBook_thenUpdated() {
        final Book book = createRandomBook();
        final String location = createBookAsUri(book);

        book.setId(Long.parseLong(location.split("api/books/")[1]));
        book.setAuthor("newAuthor");
        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .put(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("newAuthor", response.jsonPath()
            .get("author"));

    }

    @Test
    public void whenDeleteCreatedBook_thenOk() {
        final Book book = createRandomBook();
        final String location = createBookAsUri(book);

        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenTryToCreateBannedBook_thenError() {
        final  BannedBook bannedBook = new BannedBook( "Livre_Interdit","auteur_Interdit");
        final Book book =new Book("Livre_Interdit","auteur_Interdit");
        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(bannedBook)
                .post(getApiBannedBookRoot());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        final Response response2 = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .post(getApiRoot());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response2.getStatusCode());
    }
    // ===============================

    private Book createRandomBook() {
        final Book book = new Book();
        book.setTitle(randomAlphabetic(10));
        book.setAuthor(randomAlphabetic(15));
        return book;
    }

    private String createBookAsUri(Book book) {
        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .post(getApiRoot());
        return getApiRoot() + "/" + response.jsonPath()
            .get("id");
    }

}