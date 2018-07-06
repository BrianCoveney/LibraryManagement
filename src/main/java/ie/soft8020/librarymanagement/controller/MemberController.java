package ie.soft8020.librarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.repository.IMemberRepository;

@Controller
public class MemberController {
	
	@Autowired
	IMemberRepository memberRepository;
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(Model model) {
		List<Member> members = memberRepository.findAll();
		model.addAttribute("memberskey", members);
		return "index";
	}
		
}
