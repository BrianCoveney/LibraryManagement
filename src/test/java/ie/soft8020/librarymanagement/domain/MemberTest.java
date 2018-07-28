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
import static org.hamcrest.CoreMatchers.not;
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

        double fine = calculator.calculateFine(child);
        child.setFinesOutstanding(fine);

        System.out.println("Days: " + child.getDaysOnLoan());
        System.out.println("Days over: " + child.getDaysOverLimit());
        assertThat(child.getFinesOutstanding(), equalTo(2.0));
    }

    @Test
    public void testGetFinesOutstandingForChild_FromChild() {
        double fine = child.calculateFine(child);
        child.setFinesOutstanding(fine);
        assertThat(child.getFinesOutstanding(), equalTo(2.0));
    }

    @Test
    public void testGetFinesOutstandingForAdult() {
        FineCalculator calculator = new FineCalculator();
        double fine = calculator.calculateFine(adult);
        adult.setFinesOutstanding(fine);
        assertThat(adult.getFinesOutstanding(), equalTo(0.25));

        // Adult borrows more books that have the same days overdue.
        adult.setBooks(books);
        adult.setLoans(loans);
        double newFine = calculator.calculateFine(adult);
        double currentAdultFine = adult.getFinesOutstanding();
        adult.setFinesOutstanding(newFine);
        assertThat(adult.getFinesOutstanding(), equalTo(currentAdultFine * 2));
    }

    @Test
    public void testGetFinesOutstandingForAdult_FromAdult() {
        double fine = adult.calculateFine(adult);
        adult.setFinesOutstanding(fine);
        assertThat(adult.getFinesOutstanding(), equalTo(0.25));

        // Adult borrows more books that have the same days overdue.
        adult.setBooks(books);
        adult.setLoans(loans);
        double newFine = adult.calculateFine(adult);
        double currentAdultFine = adult.getFinesOutstanding();
        adult.setFinesOutstanding(newFine);
        assertThat(adult.getFinesOutstanding(), equalTo(currentAdultFine * 2));
    }

    @Test
    public void testGetFinesOutstandingForChild_WithNoBooksOverDue() {
        // Create child member
        Member child = MemberFactory.createMember("Tom Jones", childDateOfBirth);
        assertThat(child, instanceOf(Child.class));

        // Set the members loans
        List<Loan> loans = createListOfLoans("2018-06-01", "2018-06-03");
        child.setLoans(loans);

        child.updateFine(child);

        assertThat(child.getFinesOutstanding(), equalTo(0.0));
    }

    @Test
    public void testGetFinesOutstandingForChild_WithNoBooksOverDue_FromChild() {
        // Create child member
        Member child = MemberFactory.createMember("Tom Jones", childDateOfBirth);
        assertThat(child, instanceOf(Child.class));

        // Set the members loans
        List<Loan> loans = createListOfLoans("2018-06-01", "2018-06-03");
        child.setLoans(loans);

        double fine = child.calculateFine(child);
        assertThat(fine, equalTo(0.0));

        child.setFinesOutstanding(fine);
        assertThat(child.getFinesOutstanding(), equalTo(0.0));
    }

    @Test
    public void testGetFinesOutstandingForAdult_WithNoBooksOverDue() {
        // Create child member
        Member adult = MemberFactory.createMember("Billy Bob", adultDateOfBirth);
        assertThat(adult, instanceOf(Adult.class));

        // Add Loan with zero days overdue
        List<Loan> loans = createListOfLoans("2018-06-01", "2018-06-15");
        adult.setLoans(loans);

        // Update members fine when no books are overdue
        adult.updateFine(adult);
        assertThat(adult.getFinesOutstanding(), equalTo(0.0));

        // Add another Loan. This time it is overdue by one day
        List<Loan> loansOver = createListOfLoans("2018-06-01", "2018-06-16");
        adult.setLoans(loansOver);
        adult.updateFine(adult);

        assertThat(adult.getLoans(), hasSize(2));
        assertThat("Validate that one day overdue will incur a fine",
                adult.getFinesOutstanding(), equalTo(0.25));
    }

    @Test
    public void testGetFinesOutstandingForAdult_WithNoBooksOverDue_FromAdult() {
        // Create child member
        Member adult = MemberFactory.createMember("Billy Bob", adultDateOfBirth);
        assertThat(adult, instanceOf(Adult.class));

        // Add Loan with zero days overdue
        List<Loan> loans = createListOfLoans("2018-06-01", "2018-06-15");
        adult.setLoans(loans);

        // Create FineCalculator to calculate days on loan and fine
        double fine = adult.calculateFine(adult);
        assertThat(fine, equalTo(0.0));

        adult.setFinesOutstanding(fine);
        assertThat(adult.getFinesOutstanding(), equalTo(0.0));

        // Add another Loan with book overdue by one day for adult
        List<Loan> loansOver = createListOfLoans("2018-06-01", "2018-06-16");
        adult.setLoans(loansOver);

        double fineOver = adult.calculateFine(adult);
        assertThat(fineOver, not(0.0));
        assertThat(fineOver, equalTo(0.25));

        assertThat(adult.getLoans(), hasSize(2));
    }



    private List<Loan> createListOfLoans(String dateOne, String dateTwo) {
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
