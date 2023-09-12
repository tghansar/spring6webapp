package com.example.spring6webapp.bootstrap;

import com.example.spring6webapp.domain.Author;
import com.example.spring6webapp.domain.Book;
import com.example.spring6webapp.domain.Publisher;
import com.example.spring6webapp.repositories.AuthorRepository;
import com.example.spring6webapp.repositories.BookRepository;
import com.example.spring6webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*
* Class representing data that will be available on startup.
* Uses the H2 (in-memory) database.
* */

@Component //=> Spring Bean
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    /*
    * On startup the constructor will allow Spring to autowire the implementations
    * of the repository automatically to start up the repositories that are being
    * provided by the Spring Data JPA.
    * */

    public BootstrapData(AuthorRepository authorRepository,
                         BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) {
        // Book 1
        Author author1 = new Author("Eric", "Evans");
        Book book1 = new Book("Domain Driven Design", "0321125215");

        Author author1Saved = authorRepository.save(author1);
        Book book1Saved = bookRepository.save(book1);

        // Book 2
        Author author2 = new Author("Rod", "Johnson");
        Book book2 = new Book("J2EE Development without EJB", "978-0-764-57390-3");

        Author author2Saved = authorRepository.save(author2);
        Book book2Saved = bookRepository.save(book2);

        // associate authors with books
        author1Saved.getBooks().add(book1Saved);
        author2Saved.getBooks().add(book2Saved);
        book1Saved.getAuthors().add(author1Saved);
        book2Saved.getAuthors().add(author2Saved);

        // course assignment 1 - add publishers
        Publisher publisher1 = new Publisher("Addison-Wesley Professional",
                "221 River Street",
                "Hoboken",
                "New Jersey",
                "07030");

        Publisher publisherSaved = publisherRepository.save(publisher1);

        book1Saved.setPublisher(publisherSaved);
        book2Saved.setPublisher(publisherSaved);

        // persisting all changes
        authorRepository.save(author1Saved);
        authorRepository.save(author2Saved);
        bookRepository.save(book1Saved);
        bookRepository.save(book2Saved);

        System.out.println("---In Bootstrap---");
        System.out.println("Author Count: " + authorRepository.count());
        System.out.println("Book Count: " + bookRepository.count());
        System.out.println("Publisher Count: " + publisherRepository.count());
        System.out.println("-------------------");
    }
}
