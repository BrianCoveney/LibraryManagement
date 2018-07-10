package ie.soft8020.librarymanagement.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.service.IMemberService;

@Controller
public class MemberController {
	
	@Autowired
	IMemberService memberService;
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(Model model) {
//		List<Member> members = memberService.findAll().stream()
//				.limit(3).collect(Collectors.toList());
		
		List<Member> members = memberService.findAll();
				
		model.addAttribute("memberskey", members);
		return "index";
	}
		
}
