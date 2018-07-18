package ie.soft8020.librarymanagement.domain;


import ie.soft8020.librarymanagement.repository.IBookRepository;
import ie.soft8020.librarymanagement.util.Const;
import ie.soft8020.librarymanagement.util.DateUtilility;
import ie.soft8020.librarymanagement.util.FineCalculator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        assertThat("Testing the correct period of days is returned",
                DateUtilility.calculatePeriodBetweenDays(loanDateOne, returnDateOne), equalTo(15));

        assertThat("Testing the correct period of days is returned",
                DateUtilility.calculatePeriodBetweenDays(loanDateTwo, returnDateTwo), equalTo(8));

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
        loans.add(loanTwo);


        // Create 'Adult' and 'Child' objects. Then confirm they are of the correct type and book limit allowance.
        adult = MemberFactory.createMember("Tom Jones", adultDateOfBirth);
        assertThat(adult, instanceOf(Adult.class));
        assertThat(adult.getLoanLength(), equalTo(Const.LoanLength.MAX_LENGTH_OF_DAYS_ADULT_CAN_BORROW)); // = 14

        child = MemberFactory.createMember("Tom Jones", childDateOfBirth);
        assertThat(child, instanceOf(Child.class));
        assertThat(child.getLoanLength(), equalTo(Const.LoanLength.MAX_LENGTH_OF_DAYS_CHILD_CAN_BORROW)); // = 7

        // Add the list of 'books' and 'loans' to each member, and validate.
        adult.setBooks(books);
        adult.setLoans(loans);
        assertThat(adult.getBooks(), hasSize(2));
        assertThat(adult.getLoans(), hasSize(2));
        child.setBooks(books);
        child.setLoans(loans);
        assertThat(child.getBooks(), hasSize(2));
        assertThat(child.getLoans(), hasSize(2));

    }

    @Test
    public void testGetFinesOutstandingForChild() {
        FineCalculator calculator = new FineCalculator();
        double daysOverLoanLimit = calculator.getDaysOverLoanLimit(child);
        assertThat(daysOverLoanLimit, equalTo(9.0));
        double fine = calculator.calculateFine(child, daysOverLoanLimit);
        child.setFinesOutstanding(fine);
        assertThat(child.getFinesOutstanding(), equalTo(daysOverLoanLimit * Const.FineAccrued.FINE_VALUE));
    }

    @Test
    public void testGetFinesOutstandingForAdult() {
        FineCalculator calculator = new FineCalculator();
        double daysOverLoanLimit = calculator.getDaysOverLoanLimit(adult);
        double fine = calculator.calculateFine(adult, daysOverLoanLimit);
        adult.setFinesOutstanding(fine);
        assertThat(adult.getFinesOutstanding(), equalTo(daysOverLoanLimit * Const.FineAccrued.FINE_VALUE));

        // Adult borrows more books that have the same days overdue.
        adult.setBooks(books);
        adult.setLoans(loans);
        double newDaysOverLoanLimit = calculator.getDaysOverLoanLimit(adult);
        double newFine = calculator.calculateFine(adult, newDaysOverLoanLimit);
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
        List<Loan> loans = createListOfLoans();
        child.setLoans(loans);

        // Create FineCalculator to calculate days on loan and fine
        FineCalculator calculator = new FineCalculator();

        double daysOverLoanLimit = calculator.getDaysOverLoanLimit(child);
        assertThat(daysOverLoanLimit, equalTo(0.0));

        double fine = calculator.calculateFine(child, daysOverLoanLimit);
        assertThat(fine, equalTo(0.0));

        child.setFinesOutstanding(fine);
        assertThat(child.getFinesOutstanding(), equalTo(0.0));

    }

    private List<Loan> createListOfLoans() {
        // Create loan date that does not exceed child's allowance
        Date loanDateOne = DateUtilility.parseStringToDate("2018-06-01");
        Date returnDateOne = DateUtilility.parseStringToDate("2018-06-03");
        Date loanDateTwo = DateUtilility.parseStringToDate("2018-06-01");
        Date returnDateTwo = DateUtilility.parseStringToDate("2018-06-03");

        // Create a loan objects with those dates
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
        List<Loan> loans = new ArrayList<>();
        loans.add(loanOne);
        loans.add(loanTwo);
        return loans;
    }

}
