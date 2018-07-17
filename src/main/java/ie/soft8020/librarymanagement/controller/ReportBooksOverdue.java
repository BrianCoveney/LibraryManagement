package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReportBooksOverdue {

    @Autowired
    IBookService bookService;

	@RequestMapping(value="/reportoverdue", method=RequestMethod.GET)
	public String reportBookOverdue(Model model) {
		List<Book> books = bookService.findAllBooksOverdue();
		model.addAttribute("books", books);

		List<Member> members = new ArrayList<>();
        for (Book b : books) {
            members = b.getMembers();
        }
        model.addAttribute("members", members);


		return "reportoverdue";
	}

}
