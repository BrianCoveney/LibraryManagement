package ie.soft8020.librarymanagement.handlers;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.forms.LoanForm;
import ie.soft8020.librarymanagement.service.IBookService;
import io.netty.handler.codec.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoanHandler {

    @Autowired
    IBookService bookService;

    @RequestMapping(value = "/loan/")
    public String searchLoans(LoanForm loanForm) { return "loan"; }


    @RequestMapping(value = "/loan/{memberIDParam}/{bookIDParam}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Map<Book, Member> searchLoans(
                            @PathVariable long memberIDParam,
                            @PathVariable long bookIDParam) {

        Map<Book, Member> mapBooksMembers = new LinkedHashMap<>();

        int memberId = (int) memberIDParam;
        int bookId = (int) bookIDParam;

        List<Book> books = bookService.searchBooksUnavailable(memberId, bookId);
        Book book = new Book();
        book.setBooks(books);

        List<Member> members;
        for (int i = 0; i < books.size(); i++) {
            members = books.get(i).getMembers();
            mapBooksMembers.put(books.get(i), members.get(i));
        }

        return mapBooksMembers;
    }
}
