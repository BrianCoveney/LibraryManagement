package ie.soft8020.librarymanagement.domain;

import ie.soft8020.librarymanagement.util.Const;
import ie.soft8020.librarymanagement.util.DateUtilility;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public abstract class Member {

	@Id
	private int memberID;

	private String name;
	private String address;
	private Date dateOfBirth;

	public Member(String name, Date dateOfBirth) {
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}

	public abstract List<Loan> getLoans();

	public abstract void setLoans(List<Loan> loan);

	public abstract List<Book> getBooks();

	public abstract void setBooks(List<Book> books);

	public abstract Double getFinesOutstanding();

	public abstract void setFinesOutstanding(Double finesOutstanding);

	public abstract int getLoanLimit();

	public abstract void setLoanLimit(int loanLimit);

	public abstract int getLoanLength();

	public abstract void setLoanLength(int loanLength);

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int setTotalDaysOverLoanLimit(List<Loan> loans, Member member) {
		int daysPastLoanLimit = 0;

		// For each loan in the list
		for (Loan loan : loans) {
			// We get the num of days books have been borrowed
			int daysOnLoan = DateUtilility.calculatePeriodBetweenDays(loan.getLoanDate(), loan.getReturnDate());

			// We check weather the member is an Adult or Child.
			// When the daysOnLoan are over the members' limit, we increment a value and pass it to daysPastLoanLimit.
			if (member instanceof Adult) {
				if (daysOnLoan > Const.LoanLength.MAX_NUMBER_OF_DAYS_ADULT_CAN_BORROW) {
					daysPastLoanLimit += daysOnLoan - Const.LoanLength.MAX_NUMBER_OF_DAYS_ADULT_CAN_BORROW;
				}
			}
			else if (member instanceof Child) {
				if (daysOnLoan > Const.LoanLength.MAX_NUMBER_OF_DAYS_CHILD_CAN_BORROW) {
					daysPastLoanLimit += daysOnLoan - Const.LoanLength.MAX_NUMBER_OF_DAYS_CHILD_CAN_BORROW;
				}
			}
		}
		return daysPastLoanLimit;
	}
}
