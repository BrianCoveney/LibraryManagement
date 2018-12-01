package ie.soft8020.librarymanagement.handlers;


import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MembersHandler {

    @Autowired
    IMemberService memberService;

    @RequestMapping(value = "/members/{memberID}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Member getMembersEndpoint(@PathVariable("memberID") int id) {
        Member member = memberService.getMemberWithBooks(id);
        member.updateFine(member);
        return member;
    }

}
