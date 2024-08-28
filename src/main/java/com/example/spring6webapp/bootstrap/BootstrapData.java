package com.example.spring6webapp.bootstrap;

import com.example.spring6webapp.domain.Author;
import com.example.spring6webapp.domain.Book;
import com.example.spring6webapp.domain.Publisher;
import com.example.spring6webapp.repositories.AuthorRepository;
import com.example.spring6webapp.repositories.BookRepository;
import com.example.spring6webapp.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/*
* Class representing data that will be available on startup.
* Uses the H2 (in-memory) database.
* */

@Slf4j
@Component //=> Spring Bean
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    /*
    * On startup the constructor will allow Spring to autowire the implementations
    * of the repository automatically to start up the repositories that are being
    * provided by the Spring Data JPA.
    * */

    @Override
    public void run(String... args) {
        Publisher publisher = Publisher.builder()
                .publisherName("Addison-Wesley Professional")
                .address("221 River Street")
                .city("Hoboken")
                .state("New Jersey")
                .zip("07030")
                .build();
        Publisher savedPublisher = publisherRepository.save(publisher);

        Author author1 = Author.builder()
                .firstName("Eric")
                .lastName("Evans")
                .books(new HashSet<>())
                .build();
        Book book1 = Book.builder()
                .title("Domain Driven Design")
                .isbn("0321125215")
                .authors(new HashSet<>())
                .build();
        saveAuthorAndBook(author1, book1, savedPublisher);

        Author author2 = Author.builder()
                .firstName("Rod")
                .lastName("Johnson")
                .books(new HashSet<>())
                .build();
        Book book2 = Book.builder()
                .title("J2EE Development without EJB")
                .isbn("978-0-764-57390-3")
                .authors(new HashSet<>())
                .build();
        saveAuthorAndBook(author2, book2, savedPublisher);

        printCounts();
    }

    private void saveAuthorAndBook(Author author, Book book, Publisher publisher) {
        author.getBooks().add(book);
        book.getAuthors().add(author);

        book.setPublisher(publisher);

        authorRepository.save(author);
        bookRepository.save(book);
    }

    private void printCounts() {
        log.info("---In Bootstrap---");
        log.info("Book Count: " + bookRepository.count());
        log.info("Author Count: " + authorRepository.count());
        log.info("Publisher Count: " + publisherRepository.count());
        log.info("-------------------");
    }
}
