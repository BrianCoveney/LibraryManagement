package ie.soft8020.librarymanagement.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

public abstract class Member {

	@Id
	private int memberID;

	private String name;
	private String address;
	private Date dateOfBirth;
	private int loanLimit;
	private int loanLength;
	
	
	public abstract List<Loan> getLoans();

	public abstract void setLoans(List<Loan> loan);

	public abstract List<Book> getBooks();

	public abstract void setBooks(List<Book> books);
	
	public abstract Double getFinesOutstanding();
	
	public abstract void setFinesOutstanding(Double finesOutstanding);

	
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

	public int getLoanLimit() {
		return loanLimit;
	}

	public void setLoanLimit(int loanLimit) {
		this.loanLimit = loanLimit;
	}

	public int getLoanLength() {
		return loanLength;
	}

	public void setLoanLength(int loanLength) {
		this.loanLength = loanLength;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}