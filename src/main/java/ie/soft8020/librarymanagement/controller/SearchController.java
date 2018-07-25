package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.repository.IBookRepository;
import ie.soft8020.librarymanagement.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class SearchController {

    @Autowired
    IBookRepository bookService;

    private Book book;

    @GetMapping(value = "/search")
    public String search(@RequestParam(value = "book", required = false) String author, Model model) {
        if (author != null) {
            book = bookService.getByAuthor(author);
        }
        model.addAttribute("book", book);
        return "search";
    }

}