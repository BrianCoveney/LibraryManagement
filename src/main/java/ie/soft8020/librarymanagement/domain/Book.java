package ie.soft8020.librarymanagement.domain;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class Book {

	private int bookID;
	private String title;
	private String isbn;
	private String author;
	private String publisher;
	private String edition;
	private Date yearOfPublication;

	private List<Member> members;
	
	
	public Book() {
		members = Collections.<Member>emptyList();
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public Date getYearOfPublication() {
		return yearOfPublication;
	}

	public void setYearOfPublication(Date date) {
		this.yearOfPublication = date;
	}

	@Override
	public String toString() {
		String out = "Book [bookID=" + bookID + ", title=" + title + ", isbn=" + isbn + ", author=" + author
				+ ", publisher=" + publisher + ", edition=" + edition + ", yearOfPublication=" + yearOfPublication
				+ "members=[";
		for (Member member : members) {
			out += member.toString();
		}
		out += "]]";
		return out;
	}
}
