package ie.soft8020.librarymanagement.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class Book {

	private int bookID;

    @NotNull
    @Size(min=2, max=100)
    private String title;

    private String isbn;
	private String author;
	private String publisher;
	private String edition;
	private Date yearOfPublication;

	private List<Member> members;
	private List<Book> books;

	public Book() {
		members = Collections.<Member>emptyList();
		books = Collections.emptyList();
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

	public void setYearOfPublication(Date yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}

    @Override
	public String toString() {
		return "Book [bookID=" + bookID + ", title=" + title + ", isbn=" + isbn + ", author=" + author
				+ ", publisher=" + publisher + ", edition=" + edition + ", yearOfPublication=" + yearOfPublication;
	}

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


}
