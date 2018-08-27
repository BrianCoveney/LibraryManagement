package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.forms.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class LoginController {

    @RequestMapping("/users/login")
    public String addUser(UserForm userForm) {
        return "users/login";
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public String addUser(@ModelAttribute @Valid UserForm newUserForm,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("Binding result error!");
            return "users/login";
        } else {
            model.addAttribute("user", newUserForm);
            return "redirect:/";
        }
    }
}
