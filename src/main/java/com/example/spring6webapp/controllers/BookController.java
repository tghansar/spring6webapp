package com.example.spring6webapp.controllers;

import com.example.spring6webapp.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BookController {


    /*
    * Allows the use of multiple implementations using
    * Spring's Dependency Injection
    * */
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /* HTTP GET Request*/
    @RequestMapping("/books")
    public String getBooks(Model model) {

        model.addAttribute("books", bookService.findAll());

        return "books";
    }
}
