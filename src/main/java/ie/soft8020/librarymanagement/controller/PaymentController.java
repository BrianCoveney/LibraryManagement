package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.service.IMemberService;
import ie.soft8020.librarymanagement.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Validated
@Controller
public class PaymentController {

	@Autowired
	IMemberService memberService;

	private Member member;


    @GetMapping(value="/payment")
    public String payment(
            @RequestParam(value = "member", required = false) @Min(0) @Max(50) Integer memberId,
            Model model) {

        if (memberId != null) {
            member = memberService.getMemberWithBooks(memberId);
            member.updateFine(member);
        }
        model.addAttribute("memberKey", member);

        return "payment";
    }

    // The value matches the 'action' of the second form in payment.html
    @PostMapping(value = "/update")
    public String updateMember(
            @RequestParam(required=false, name="memberID") @Min(0) Integer memberId,
            @RequestParam(required=false, name="finesOutstanding") @Min(0) Double finesOutstanding) {

        if (memberId != null) {
            member = memberService.getMemberWithBooks(memberId);
            member.updateFine(member);
            double currFine  = member.getFinesOutstanding();
            if (finesOutstanding != null) {
                double calc = currFine - finesOutstanding;
                double fine = Const.round(calc, 2);

                member.setFinesOutstanding(fine);

                memberService.save(member);
            }
        }
        return "redirect:/payment";
    }
}
