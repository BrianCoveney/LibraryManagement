package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

	@Autowired
	IMemberService memberService;

	private Member member;

    @GetMapping(value="/payment")
    public String payment(
            @RequestParam(value = "member", required = false) Integer memberId,
            Model model) {

        if (memberId != null) {
            member = memberService.getMemberWithBooks(memberId);
            member.updateFine(member);
        }

        model.addAttribute("memberKey", member);

        return "payment";
    }
}
