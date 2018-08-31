package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.forms.LoanForm;
import ie.soft8020.librarymanagement.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoanController {

    @Autowired
    IBookService bookService;

    @RequestMapping(value = "/loan")
    public String searchLoans(LoanForm loanForm) { return "loan"; }


    @RequestMapping(value = "/loan", method = RequestMethod.POST)
    public String searchLoans(@ModelAttribute @Valid LoanForm loanForm,
                              BindingResult bindingResult, Model model) {

        Book book = new Book();
        List<Member> members;
        Map<Book, Member> mapBooksMembers = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            System.out.println("Binding result error!");
            return "loan";
        } else {
            int memberID = loanForm.getMemberID();
            int bookID = loanForm.getBookID();

            List<Book> books = bookService.searchBooksUnavailable(memberID, bookID);
            book.setBooks(books);

            for (int i = 0; i < books.size(); i++) {
                members = books.get(i).getMembers();
                mapBooksMembers.put(books.get(i), members.get(i));
            }
        }

        model.addAttribute("loanForm", loanForm);
        model.addAttribute("booksMembers", mapBooksMembers);

        return "loan";
    }
}
