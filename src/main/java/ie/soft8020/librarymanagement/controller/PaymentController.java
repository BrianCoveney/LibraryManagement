package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.forms.PaymentForm;
import ie.soft8020.librarymanagement.service.IMemberService;
import ie.soft8020.librarymanagement.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class PaymentController {

    @Autowired
    IMemberService memberService;

    private Member member;

    /*
    * First form
    */
    @RequestMapping(value = "/payment")
    public String findMember(PaymentForm paymentForm) {
        return "payment";
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String findMember(@ModelAttribute @Valid PaymentForm paymentForm,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("Binding result error!");
            return "payment";
        } else {
            Integer id = paymentForm.getMemberID();

            if (id != null) {
                member = memberService.getMemberWithBooks(id);
                member.updateFine(member);
            }
        }

        model.addAttribute("paymentForm", member);

        return "payment";
    }

    /*
     * Second form
     */
    @RequestMapping(value = "/update")
    public String updateMember(PaymentForm paymentForm) {
        return "update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateMember(@ModelAttribute @Valid PaymentForm paymentForm,
                               BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("Binding result error!");
            return "payment";
        } else {
            Integer id = paymentForm.getMemberID();

            if (id != null) {
                Double finesOutstanding = paymentForm.getFinesOutstanding();

                member = memberService.getMemberWithBooks(id);
                member.updateFine(member);
                double currFine = member.getFinesOutstanding();

                double calc = currFine - finesOutstanding;
                double fine = Const.round(calc, 2);

                member.setFinesOutstanding(fine);
            }
        }

        model.addAttribute("paymentForm", member);

        return "payment";
    }
}

