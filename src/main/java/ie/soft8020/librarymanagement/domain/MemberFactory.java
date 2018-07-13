package ie.soft8020.librarymanagement.domain;

import ie.soft8020.librarymanagement.util.DateUtilility;

import java.util.Date;

public class MemberFactory {

	private MemberFactory() { }

	public static Member createMember(String name, Date dateOfBirth) {

		int years = DateUtilility.calculatePeriodBetweenYears(dateOfBirth);

		if (years < 18) {
			return new Child(name, dateOfBirth);
		} else {
			return new Adult(name, dateOfBirth);
		}
	}
}
