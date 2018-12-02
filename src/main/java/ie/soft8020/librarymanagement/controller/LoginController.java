package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.forms.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String addUser(UserForm userForm) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String addUser(@RequestBody @ModelAttribute @Valid UserForm newUserForm,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("Binding result error!");
            return "login";
        } else {

            String userName = newUserForm.getUsername();
            String password = newUserForm.getPassword();

            if (userName.equals("admin") && password.equals("password")) {
                return "redirect:/payment";
            }

            model.addAttribute("userForm", newUserForm);
            return "redirect:/search";
        }
    }
}
