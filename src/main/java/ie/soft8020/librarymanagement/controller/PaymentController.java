package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.service.IMemberService;
import ie.soft8020.librarymanagement.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping(value = "/update")
    public String updateMember(
            @RequestParam(required=false, name="memberID")  Integer memberId,
            @RequestParam(required=false, name="finesOutstanding")  Double finesOutstanding) {

        if (memberId != null) {
            member = memberService.getMemberWithBooks(memberId);
            member.updateFine(member);
            double currFine  = member.getFinesOutstanding();
            if (finesOutstanding != null) {
                double calc = currFine - finesOutstanding;
                double fine = Const.round(calc, 2);

                member.setFinesOutstanding(fine);
            }
        }
        return "redirect:/payment";
    }

}
