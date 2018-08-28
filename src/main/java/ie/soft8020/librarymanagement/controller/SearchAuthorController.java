package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.domain.BookList;
import ie.soft8020.librarymanagement.forms.SearchAuthorForm;
import ie.soft8020.librarymanagement.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SearchAuthorController {

    @Autowired
    IBookService bookService;

    private List<Book> books;
    private BookList bookList = new BookList();

    @RequestMapping(value = "/search")
    public String searchBooksByAuthor(SearchAuthorForm searchAuthorForm) { return "search"; }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchBooksByAuthor(@ModelAttribute @Valid SearchAuthorForm searchAuthorForm,
                                      BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("Binding result error!");
            return "search";
        } else {
            String sanitizedAuthorStr = sanitizeForSearch(searchAuthorForm.getAuthor());
            books = bookService.getBooksByAuthor(sanitizedAuthorStr);
            for (Book book : books) {
                model.addAttribute("searchAuthorForm", book);
            }
        }

        model.addAttribute("booksAll", bookService.findAll());

        return "search";
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
