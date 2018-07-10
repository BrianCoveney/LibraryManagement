package ie.soft8020.librarymanagement.domain;

import java.time.LocalDate;
import java.util.Date;

import ie.soft8020.librarymanagement.util.DateUtil;

public class MemberFactory {
	
	private MemberFactory() { }

	public static Member createMember(String name, Date dateOfBirth) {
		
		int years = DateUtil.calculateAge(dateOfBirth, LocalDate.now());
		
		if (years < 18) {
			return new Child(name, dateOfBirth);
		} else {
			return new Adult(name, dateOfBirth);
		}
	}
}
