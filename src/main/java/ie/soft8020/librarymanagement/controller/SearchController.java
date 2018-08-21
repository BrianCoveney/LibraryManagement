package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Controller
public class SearchController implements WebMvcConfigurer {

    @Autowired
    IBookService bookService;;

    private List<Book> books;
    private Book book;


    @GetMapping(value = "/search")
    public String searchBooksByAuthor(
            @RequestParam(value = "book", required = false) String author,
            Model model) {

        String sanitizedAuthorStr = sanitizeForSearch(author);

        if (sanitizedAuthorStr != null) {
            books = bookService.getBooksByAuthor(sanitizedAuthorStr);
        }
        model.addAttribute("bookAuthor", books);
        model.addAttribute("booksAll", bookService.findAll());

        return "search";
    }

    @GetMapping(value = "/searchtitle")
    public String searchBooksByTitle(
            @RequestParam (value = "book", required = false) String title,
            Model model) {

        String sanitizedTitleStr = sanitizeForSearch(title);

        try {
            if (sanitizedTitleStr != null) {
                book = bookService.getByTitle(sanitizedTitleStr);
            }
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
        }

        model.addAttribute("bookTitle", book);
        model.addAttribute("booksAll", bookService.findAll());

        return "searchtitle";
    }

    private String sanitizeForSearch(String str) {
        if (str == null) {
            return null;
        }
        return str
                // Remove punctuation
                .replace("`", " ").replace("!", " ").replace("#", " ").replace("$", " ").replace("^", " ")
                .replace("&", " ").replace("[", " ").replace("]", " ").replace("{", " ").replace("}", " ").replace("|", " ")
                .replace(";", " ").replace("*", " ").replace(".", " ").replace("?", " ").replace("'", " ").replace("/", " ")
                // Prevent injection
                .replace("=", " ")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                // Remove leading/trailing whitespace
                .trim();
    }
}
