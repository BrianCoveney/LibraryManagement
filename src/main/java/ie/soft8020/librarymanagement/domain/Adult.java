package ie.soft8020.librarymanagement.domain;

import java.util.Date;

public class Adult extends Member {

	public Adult(String name, Date dateOfBirth) {
		super();
	}

	@Override
	public Double calculateFines() {
		return 50.0;
	}

	@Override
	public String toString() {
		String out = "Adult [memberID=" + getMemberID() + ", name=" + getName() + ", address=" + getAddress()
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
