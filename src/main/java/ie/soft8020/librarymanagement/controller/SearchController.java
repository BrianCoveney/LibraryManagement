package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.forms.SearchForm;
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
public class SearchController {

    @Autowired
    IBookService bookService;

    @RequestMapping(value = "/search")
    public String searchBooksByAuthor(SearchForm searchForm) { return "search"; }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchBooksByAuthor(@ModelAttribute @Valid SearchForm searchForm,
                                      BindingResult bindingResult, Model model) {

        Book book = new Book();

        if (bindingResult.hasErrors()) {
            System.out.println("Binding result error!");
            return "search";
        } else {
            String sanitizedAuthorStr = sanitizeForSearch(searchForm.getAuthor());
            String sanitizedTitleStr = sanitizeForSearch(searchForm.getTitle());

            List<Book> bookList = bookService.getBooksByAuthorOrTitle(sanitizedAuthorStr, sanitizedTitleStr);
            book.setBooks(bookList);
        }

        model.addAttribute("searchForm", book);
        model.addAttribute("booksAll", bookService.findAll());

        return "search";
    }

    private String sanitizeForSearch(String str) {
        if (str == null) {
            return null;
        }
        return str
                // Remove punctuation
                .replace("`", " ").replace("!", " ").replace("#", " ").replace("$", " ").replace("^", " ")
                .replace("&", " ").replace("[", " ").replace("]", " ").replace("{", " ").replace("}", " ")
                .replace("|", " ").replace(";", " ").replace("*", " ").replace(".", " ").replace("?", " ")
                .replace("'", " ").replace("/", " ")
                // Prevent injection
                .replace("=", " ")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                // Remove leading/trailing whitespace
                .trim();
    }
}
