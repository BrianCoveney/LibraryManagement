//package ie.soft8020.librarymanagement.controller;
//
//import ie.soft8020.librarymanagement.domain.Book;
//import ie.soft8020.librarymanagement.service.IBookService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//
//@Controller
//public class SearchTitleController {
//
//    @Autowired
//    IBookService bookService;;
//
//    private Book bookByTitle;
//
//    @GetMapping(value = "/searchtitle")
//    public String searchBooksByTitle(
//            @RequestParam (value = "book", required = false) String title,
//            Model model) {
//
//        if (title != null) {
//            bookByTitle = bookService.getByTitle(title);
//        }
//
//        model.addAttribute("bookTitle", bookByTitle);
//
//        model.addAttribute("booksAll", bookService.findAll());
//
//        return "searchtitle";
//    }
//
//}