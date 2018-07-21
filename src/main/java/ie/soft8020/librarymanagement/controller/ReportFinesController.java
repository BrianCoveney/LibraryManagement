package ie.soft8020.librarymanagement.controller;

import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReportFinesController {

	@Autowired
	IMemberService memberService;

	@RequestMapping(value="/reportfines", method=RequestMethod.GET)
	public String reportFines(Model model) {

		// We create a map for storing member id and title as the key, and
		// loan member id as the value.
		Map<String, Double> map = new LinkedHashMap<>();

		List<Member> members = memberService.findMembersWithFines();

		for (int i = 0; i < members.size(); i++) {
		    Member member = members.get(i);
		    map.put(member.getName(), member.getFinesOutstanding());
		}

		model.addAttribute("memberLoanMap", map);

		return "reportfines";
	}

}
