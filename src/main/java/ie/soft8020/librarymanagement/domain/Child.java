package ie.soft8020.librarymanagement.domain;

import java.util.Date;

public class Child extends Member {

	public Child(String name, Date dateOfBirth) {
		super();
	}

	@Override
	public Double calculateFines() {
		return 108.0;
	}

	@Override
	public String toString() {
		String out = "Child [memberID=" + getMemberID() + ", name=" + getName() + ", address=" + getAddress()
				+ ", dateOfBirth=" + getDateOfBirth() + ", loanLimit=" + getLoanLimit() + ", loanLength="
				+ getLoanLength() + ", finesOutstanding=" + getFinesOutstanding() + "books=[";
		for (Book book : getBooks()) {
			out += book.toString();
		}
		out += "]]";
		out += "loans=[";
		for (Loan loan : getLoans()) {
			out += loan.toString();
		}
		out += "]]";
		return out;
	}
}
