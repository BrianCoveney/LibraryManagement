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
public class ReportOverdueController {

    @Autowired
    IBookService bookService;

	@RequestMapping(value="/reportoverdue", method=RequestMethod.GET)
	public String reportBookOverdue(Model model) {

	    // We create a map for storing book id and title as the key, and
        // member id and name as the value.
		Map<Book, Member> map = new LinkedHashMap<>();

		// We create a book list that retrieves data from the db
		List<Book> books = bookService.findBooksLoanedByMembers();

		// For each book retrieved, we find the associated member(s).
        // Then we copy the list of members to our ArrayList.
        // For each book we put the book and associated member in out map
        for (int i = 0; i < books.size(); i++) {
            List<Member> members = books.get(i).getMembers();
            map.put(books.get(i), members.get(i));
        }

        // Add our map to the model, which our Thymeleaf view (reportoverdue.html) will get for processing.
        model.addAttribute("bookmembermap", map);

		return "reportoverdue";
	}

}
