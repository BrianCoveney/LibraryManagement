package ie.soft8020.librarymanagement.domain;

import ie.soft8020.librarymanagement.util.Const;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class Adult extends Member {

	private int loanLimit;
	private int loanLength;


	public Adult(String name, Date dateOfBirth) {
		super(name, dateOfBirth);
		setLoanLimit(Const.LoanLimit.MAX_NUMBER_OF_BOOKS_FOR_ADULT);
		setLoanLength(Const.LoanLength.MAX_LENGTH_OF_DAYS_ADULT_CAN_BORROW);
	}

	@Override
	public int getLoanLimit() {
		return loanLimit;
	}

	@Override
	public void setLoanLimit(int loanLimit) {
		this.loanLimit = loanLimit;
	}

	@Override
	public int getLoanLength() {
		return loanLength;
	}

	@Override
	public void setLoanLength(int loanLength) {
		this.loanLength = loanLength;
	}

	@Override
	public String toString() {
		String out = "Adult [memberID=" + getMemberID() + ", name=" + getName() + ", address=" + getAddress()
				+ ", dateOfBirth=" + getDateOfBirth() + ", loanLimit=" + getLoanLimit() + ", loanLength="
				+ getLoanLength() + ", finesOutstanding=" + getFinesOutstanding() + "\n"
				+ "books=[" + getListToString(getBooks()) + "]]\n"
				+ "loan=[" + getListToString(getLoans()) + "]]";
		return out;
	}

	private String getListToString(List<?> list) {
		return list.stream().map(e -> e.toString()).collect(Collectors.joining(","));
	}

}
