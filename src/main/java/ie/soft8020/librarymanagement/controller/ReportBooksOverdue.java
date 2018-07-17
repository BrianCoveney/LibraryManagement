package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class ReportBooksOverdue {

    @Autowired
    IBookService bookService;

	@RequestMapping(value="/reportoverdue", method=RequestMethod.GET)
	public String reportBookOverdue(Model model) {

		Map<Book, Member> map = new LinkedHashMap<>();
		List<Book> books = bookService.findAllBooksOverdue();

		List<Member> members = new ArrayList<>();
		for (Book b : books) {
            members = b.getMembers();
        }

		for (int i = 0; i < books.size(); i++) {
			map.put(books.get(i), members.get(i));
		}

        model.addAttribute("bookmembermap", map);


		return "reportoverdue";
	}

}
