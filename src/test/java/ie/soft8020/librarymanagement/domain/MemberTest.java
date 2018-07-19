package ie.soft8020.librarymanagement.domain;


import ie.soft8020.librarymanagement.repository.IBookRepository;
import ie.soft8020.librarymanagement.util.DateUtilility;
import ie.soft8020.librarymanagement.util.FineCalculator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ie.soft8020.librarymanagement.util.Const.LoanLength.MAX_ADULT_DAYS;
import static ie.soft8020.librarymanagement.util.Const.LoanLength.MAX_CHILD_DAYS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;


public class MemberTest {

    @Autowired
    IBookRepository repo;

    private Member adult, child;
    private Date adultDateOfBirth = DateUtilility.parseStringToDate("1998-03-01");
    private Date childDateOfBirth = DateUtilility.parseStringToDate("2010-07-11");
    private List<Book> books = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();


    @Before
    public void setUp() {

        // Create loan and return dates
        Date loanDateOne = DateUtilility.parseStringToDate("2018-06-01");
        Date returnDateOne = DateUtilility.parseStringToDate("2018-06-16");
        Date loanDateTwo = DateUtilility.parseStringToDate("2018-06-01");
        Date returnDateTwo = DateUtilility.parseStringToDate("2018-06-09");

        // Test dates
        assertThat(DateUtilility.calculatePeriodBetweenDays(loanDateOne, returnDateOne),
                equalTo(MAX_ADULT_DAYS + 1));

        assertThat(DateUtilility.calculatePeriodBetweenDays(loanDateTwo, returnDateTwo),
                equalTo(MAX_CHILD_DAYS + 1));

        // Create books and loans
        Book bookOne = new Book();
        bookOne.setBookID(1);
        bookOne.setTitle("Effective Java");
        Book bookTwo = new Book();
        bookTwo.setBookID(2);
        bookTwo.setTitle("Learn Python");

        Loan loanOne = new Loan();
        loanOne.setBookId(1);
        loanOne.setMemberId(1);
        loanOne.setLoanDate(loanDateOne);
        loanOne.setReturnDate(returnDateOne);
        Loan loanTwo = new Loan();
        loanTwo.setBookId(2);
        loanTwo.setMemberId(2);
        loanTwo.setLoanDate(loanDateTwo);
        loanTwo.setReturnDate(returnDateTwo);

        // Add items to lists
        books.add(bookOne);
        books.add(bookTwo);
        loans.add(loanOne);
//        loans.add(loanTwo);


        // Create 'Adult' and 'Child' objects. Then confirm they are of the correct type and book limit allowance.
        adult = MemberFactory.createMember("Tom Jones", adultDateOfBirth);
        assertThat(adult, instanceOf(Adult.class));
        assertThat(adult.getLoanLength(), equalTo(MAX_ADULT_DAYS)); // = 14

        child = MemberFactory.createMember("Tom Jones", childDateOfBirth);
        assertThat(child, instanceOf(Child.class));
        assertThat(child.getLoanLength(), equalTo(MAX_CHILD_DAYS)); // = 7

        // Add the list of 'books' and 'loans' to each member, and validate.
        adult.setBooks(books);
        adult.setLoans(loans);
        assertThat(adult.getBooks(), hasSize(2));
        assertThat(adult.getLoans(), hasSize(1));
        child.setBooks(books);
        child.setLoans(loans);
        assertThat(child.getBooks(), hasSize(2));
        assertThat(child.getLoans(), hasSize(1));

    }

    @Test
    public void testGetFinesOutstandingForChild() {
        FineCalculator calculator = new FineCalculator();

        double daysOnLoan = calculator.getDaysOnLoan(child);
        System.out.println(daysOnLoan);
        double fine = calculator.calculateFine(child);
        child.setFinesOutstanding(fine);

//        System.out.println(fine);


//        assertThat(child.getFinesOutstanding(), equalTo(2.25));
    }
//
//    @Test
//    public void testGetFinesOutstandingForAdult() {
//        FineCalculator calculator = new FineCalculator();
//        double fine = calculator.calculateFine(adult);
//        adult.setFinesOutstanding(fine);
//        assertThat(adult.getFinesOutstanding(), equalTo(0.25));
//
//        // Adult borrows more books that have the same days overdue.
//        adult.setBooks(books);
//        adult.setLoans(loans);
//        double newFine = calculator.calculateFine(adult);
//        double currentAdultFine = adult.getFinesOutstanding();
//        adult.setFinesOutstanding(newFine);
//        assertThat(adult.getFinesOutstanding(), equalTo(currentAdultFine * 2));
//    }

    @Test
    public void testGetFinesOutstandingForChild_WithNoBooksOverDue() {
        // Create child member
        Member child = MemberFactory.createMember("Tom Jones", childDateOfBirth);
        assertThat(child, instanceOf(Child.class));

        // Set the members loans
        List<Loan> loans = createListOfLoans("2018-06-01", "2018-06-03");
        child.setLoans(loans);

        // Create FineCalculator to calculate days on loan and fine
        FineCalculator calculator = new FineCalculator();

        double fine = calculator.calculateFine(child);
        assertThat(fine, equalTo(0.0));

        child.setFinesOutstanding(fine);
        assertThat(child.getFinesOutstanding(), equalTo(0.0));
    }

    @Test
    public void testGetFinesOutstandingForAdult_WithNoBooksOverDue() {
        // Create child member
        Member adult = MemberFactory.createMember("Billy Bob", adultDateOfBirth);
        assertThat(adult, instanceOf(Adult.class));

        // Set the members loans
        List<Loan> loans = createListOfLoans("2018-06-01", "2018-06-15");
        adult.setLoans(loans);

        // Create FineCalculator to calculate days on loan and fine
        FineCalculator calculator = new FineCalculator();

        double fine = calculator.calculateFine(adult);
        assertThat(fine, equalTo(0.0));

        adult.setFinesOutstanding(fine);
        assertThat(adult.getFinesOutstanding(), equalTo(0.0));
    }



    private List<Loan> createListOfLoans(String dateOne, String dateTwo) {
        // Create loan date that does not exceed child's allowance
        Date loanDate = DateUtilility.parseStringToDate(dateOne);
        Date returnDate = DateUtilility.parseStringToDate(dateTwo);


        // Create a loan objects with those dates
        Loan loanOne = new Loan();
        loanOne.setBookId(1);
        loanOne.setMemberId(1);
        loanOne.setLoanDate(loanDate);
        loanOne.setReturnDate(returnDate);
        Loan loanTwo = new Loan();
        loanTwo.setBookId(2);
        loanTwo.setMemberId(2);
        loanTwo.setLoanDate(loanDate);
        loanTwo.setReturnDate(returnDate);
        List<Loan> loans = new ArrayList<>();
        loans.add(loanOne);
        loans.add(loanTwo);
        return loans;
    }

}
