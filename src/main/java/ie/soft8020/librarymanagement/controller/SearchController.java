package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.repository.IBookRepository;
import ie.soft8020.librarymanagement.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class SearchController {

    @Autowired
    IBookService bookService;;

    private List<Book> bookListByAuthor;
    private Book bookByTitle;


    @GetMapping(value = "/search")
    public String searchBooksByAuthor(
            @RequestParam (value = "book", required = false) String author,
            Model model) {

        if (author != null) {
            bookListByAuthor = bookService.getBooksByAuthor(author);
        }

        model.addAttribute("bookAuthor", bookListByAuthor);

        model.addAttribute("booksAll", bookService.findAll());

        return "search";
    }

    @GetMapping(value = "/searchtitle")
    public String searchBooksByTitle(
            @RequestParam (value = "book", required = false) String title,
            Model model) {

        if (title != null) {
            bookByTitle = bookService.getByTitle(title);
        }

        model.addAttribute("bookTitle", bookByTitle);

        model.addAttribute("booksAll", bookService.findAll());

        return "searchtitle";
    }

}