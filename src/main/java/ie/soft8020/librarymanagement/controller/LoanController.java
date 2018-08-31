package ie.soft8020.librarymanagement.controller;

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

@Controller
public class LoanController {

    @Autowired
    IBookService bookService;

    @RequestMapping(value = "/loan")
    public String searchBooks(LoanForm loanForm) { return "loan"; }


    @RequestMapping(value = "/loan", method = RequestMethod.POST)
    public String searchBooks(@ModelAttribute @Valid LoanForm loanForm,
                              BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("Binding result error!");
            return "loan";
        }

        int memberID = loanForm.getMemberID();
        int bookID = loanForm.getBookID();



        model.addAttribute("loanForm", loanForm);
        return "loan";
    }
}
