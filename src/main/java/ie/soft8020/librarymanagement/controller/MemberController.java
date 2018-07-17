package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MemberController {

	@Autowired
	IMemberService memberService;

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(Model model) {
		List<Member> members = memberService.findAll();
		model.addAttribute("memberskey", members);
		return "index";
	}

}
