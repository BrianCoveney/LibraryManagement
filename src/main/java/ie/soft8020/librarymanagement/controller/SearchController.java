package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.forms.SearchForm;
import ie.soft8020.librarymanagement.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.List;


@Controller
public class SearchController implements WebMvcConfigurer {

    @Autowired
    IBookService bookService;;

    private List<Book> books;
    private Book book;

    @RequestMapping(value = "/search")
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

    @RequestMapping(value = "/searchtitle")
    public String searchBooksByTitle(SearchForm searchForm) { return "searchtitle"; }

    @RequestMapping(value = "/searchtitle", method = RequestMethod.POST)
    public String searchBooksByTitle(@ModelAttribute @Valid SearchForm searchForm,
                                     BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("Binding results error!");
            return "searchtitle";
        } else {
            String sanitizedTitleStr = sanitizeForSearch(searchForm.getTitle());
            try {
                if (sanitizedTitleStr != null) {
                    book = bookService.getByTitle(sanitizedTitleStr);
                }
            } catch (EmptyResultDataAccessException e) {
                System.out.println(e.getMessage());
            }
        }

        model.addAttribute("searchForm", book);
        model.addAttribute("booksAll", bookService.findAll());

        return "searchtitle";
    }

    private String sanitizeForSearch(String str) {
        if (str == null) {
            throw new IllegalArgumentException("The argument cannot be null");
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
