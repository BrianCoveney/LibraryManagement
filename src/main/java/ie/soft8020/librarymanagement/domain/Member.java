package ie.soft8020.librarymanagement.domain;

import ie.soft8020.librarymanagement.util.Const;
import ie.soft8020.librarymanagement.util.DateUtilility;
import org.springframework.data.annotation.Id;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public abstract class Member {

	@Id
	private int memberID;

	private String name;
	private String address;
	private Date dateOfBirth;
	private Double finesOutstanding;
	private List<Loan> loans;
	private List<Book> books;

	public Member(String name, Date dateOfBirth) {
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		// When creating a member there can be situations when the List of 'books'
		// or 'loans' have not been populated. We use an empty list in our constructor,
		// to prevent a NPE, generated by toString() in this scenario.
		loans = Collections.emptyList();
		books = Collections.emptyList();
	}

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

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public Double getFinesOutstanding() {
		return finesOutstanding;
	}

	public void setFinesOutstanding(Double finesOutstanding) {
		this.finesOutstanding = finesOutstanding;
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
				if (daysOnLoan > Const.LoanLength.MAX_LENGTH_OF_DAYS_ADULT_CAN_BORROW) {
					daysPastLoanLimit += daysOnLoan - Const.LoanLength.MAX_LENGTH_OF_DAYS_ADULT_CAN_BORROW;
				}
			}
			else if (member instanceof Child) {
				if (daysOnLoan > Const.LoanLength.MAX_LENGTH_OF_DAYS_CHILD_CAN_BORROW) {
					daysPastLoanLimit += daysOnLoan - Const.LoanLength.MAX_LENGTH_OF_DAYS_CHILD_CAN_BORROW;
				}
			}
		}
		return daysPastLoanLimit;
	}
}
