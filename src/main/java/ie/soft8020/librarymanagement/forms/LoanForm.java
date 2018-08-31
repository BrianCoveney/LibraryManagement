package ie.soft8020.librarymanagement.forms;

import ie.soft8020.librarymanagement.domain.Book;
import ie.soft8020.librarymanagement.domain.Loan;
import ie.soft8020.librarymanagement.domain.Member;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.List;

public class LoanForm {

    @NotNull(message = "please enter member id")
    @Range(min = 1, max = 9, message = "Please select positive numbers only, in the range of 1 - 9")
    private Integer memberID;

    @NotNull(message = "please enter book id")
    @Range(min = 1, max = 9, message = "Please select positive numbers only, in the range of 1 - 9")
    private Integer bookID;

    private List<Book> books;
    private Member member;
    private Book book;
    private Loan loan;
    private double finesOutstanding;
    private String name;

    public Integer getMemberID() {
        return memberID;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }

    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public double getFinesOutstanding() {
        return finesOutstanding;
    }

    public void setFinesOutstanding(double finesOutstanding) {
        this.finesOutstanding = finesOutstanding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
