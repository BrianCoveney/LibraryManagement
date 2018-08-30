package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.domain.Loan;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.forms.SearchForm;
import ie.soft8020.librarymanagement.repository.IBookRepository;
import ie.soft8020.librarymanagement.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    IBookService bookService;

    @Autowired
    IBookRepository bookRepository;

    @RequestMapping(value = "/search")
    public String searchBooksByAuthor(SearchForm searchForm) { return "search"; }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchBooksByAuthor(@ModelAttribute @Valid SearchForm searchForm,
                                      BindingResult bindingResult, Model model) {

        Book book = new Book();
        Map<Book, Member> map = new LinkedHashMap<>();
        List<Member> members = new ArrayList<>();
        List<Loan> loans;

        Map<Book, Loan> mapBooksLoans = new LinkedHashMap<>();
        Map<Loan, Member> mapLoansMember = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            System.out.println("Binding result error!");
            return "search";
        } else {
            String sanitizedAuthor = sanitizeForSearch(searchForm.getAuthor());
            String sanitizedTitle = sanitizeForSearch(searchForm.getTitle());

            List<Book> books = bookRepository.findBooksLoanedWithSearch(sanitizedAuthor, sanitizedTitle);
            book.setBooks(books);

            for (int i = 0; i < books.size(); i++) {
                members = books.get(i).getMembers();
                loans = members.get(i).getLoans();
                mapBooksLoans.put(books.get(i), loans.get(i));
                mapLoansMember.put(loans.get(i), members.get(i));
            }
        }

        model.addAttribute("searchForm", book);

        // Ours maps created in the for loop above. Theses are used in the view to fill our table with
        // details for a particular book, including whether it is on loan or on shelf.
        model.addAttribute("booksLoans", mapBooksLoans);
        model.addAttribute("loansMembers", mapLoansMember);

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
