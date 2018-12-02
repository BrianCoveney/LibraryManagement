package ie.soft8020.librarymanagement.handlers;

import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.service.IMemberService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MembersWithFinesHandler {

    @Autowired
    IMemberService memberService;

	/*
	 * We use the HttpMessageConverter @ResponseBody to indicate to Spring that the return value of the method is
	 * serialized directly to the body of the HTTP Response.
	 */
	@RequestMapping(value="/reportfines/all", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String, Double> reportFinesEndpoint() {

		List<Member> members = memberService.findMembersWithFines();

		Map<String, Double> membersWithFines = new LinkedHashMap<>();
		for (Member member : members) {
			membersWithFines.put(member.getName(), member.getFinesOutstanding());
		}
		return membersWithFines;
	}
}
