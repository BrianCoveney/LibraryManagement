package ie.soft8020.librarymanagement.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Child extends Member {

	@Id
	private int memberID;
	
	private String name;
	private String address;
	private Date dateOfBirth;
	private int loanLimit;
	private int loanLength;
	private Double finesOutstanding;
	private List<Book> books; 
	
	public Child(String name, Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		this.name = name;
	}
	
	@Override
	public List<Book> getBooks() {
		return books;
	}
	
	@Override
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	@Override
	public int getMemberID() {
		return memberID;
	}
	
	@Override
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}
	
	@Override
	public String getAddress() {
		return address;
	}
	
	@Override
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	@Override
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
	public void setLoanLength(int loanLength) {
		this.loanLength = loanLength;
	}
	
	@Override
	public Double getFinesOutstanding() {
		return finesOutstanding;
	}
	
	@Override
	public void setFinesOutstanding(Double finesOutstanding) {
		this.finesOutstanding = finesOutstanding;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Child [memberID=" + memberID + ", name=" + name + ", address=" + address + ", dateOfBirth="
				+ dateOfBirth + ", loanLimit=" + loanLimit + ", loanLength=" + loanLength + ", finesOutstanding="
				+ finesOutstanding + ", books=" + books + "]";
	}	
}
