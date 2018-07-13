package ie.soft8020.librarymanagement.domain;


import ie.soft8020.librarymanagement.util.Const;
import ie.soft8020.librarymanagement.util.DateUtilility;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;


public class MemberTest {

    private Member adultMember, childMember;

    @Before
    public void setUp() {

        // Create loan and return dates
        Date loanDateOne = DateUtilility.parseStringToDate("2018-06-01");
        Date returnDateOne = DateUtilility.parseStringToDate("2018-06-22");
        Date loanDateTwo = DateUtilility.parseStringToDate("2018-06-01");
        Date returnDateTwo = DateUtilility.parseStringToDate("2018-06-15");

        // Test dates
        assertThat("Testing the correct period of days is returned",
                DateUtilility.calculatePeriodBetweenDays(loanDateOne, returnDateOne), equalTo(21));

        assertThat("Testing the correct period of days is returned",
                DateUtilility.calculatePeriodBetweenDays(loanDateTwo, returnDateTwo), equalTo(14));

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

        // Create lists to hold books and loans
        List<Book> books = new ArrayList<>();
        books.add(bookOne);
        books.add(bookTwo);
        List<Loan> loans = new ArrayList<>();
        loans.add(loanOne);
        loans.add(loanTwo);

        // Create dobs
        Date adultDateOfBirth = DateUtilility.parseStringToDate("1998-03-01");
        Date childDateOfBirth = DateUtilility.parseStringToDate("2010-07-11");

        // Create 'Adult' and 'Child' objects. Then confirm they are of the correct type and book limit allowance.
        adultMember = MemberFactory.createMember("Tom Jones", adultDateOfBirth);
        assertThat(adultMember, instanceOf(Adult.class));
        assertThat(adultMember.getLoanLength(), equalTo(Const.LoanLength.MAX_LENGTH_OF_DAYS_ADULT_CAN_BORROW)); // = 14

        childMember = MemberFactory.createMember("Tom Jones", childDateOfBirth);
        assertThat(childMember, instanceOf(Child.class));
        assertThat(childMember.getLoanLength(), equalTo(Const.LoanLength.MAX_LENGTH_OF_DAYS_CHILD_CAN_BORROW)); // = 7

        // Add the list of 'books' and 'loans' to each member, and validate.
        adultMember.setBooks(books);
        adultMember.setLoans(loans);
        assertThat(adultMember.getBooks(), hasSize(2));
        assertThat(adultMember.getLoans(), hasSize(2));
        childMember.setBooks(books);
        childMember.setLoans(loans);
        assertThat(childMember.getBooks(), hasSize(2));
        assertThat(childMember.getLoans(), hasSize(2));
    }

    // This method takes the list of loans and a member object. It will return a number calculated differently,
    // depending on the member type.
    @Test
    public void testSetTotalDaysOverLoanLimitForAdult() {
        int numDaysOverdue = adultMember.setTotalDaysOverLoanLimit(adultMember.getLoans(), adultMember);

        // For the Adult, book one is 0 days and book two is 7 days overdue.
        assertThat(numDaysOverdue, equalTo(7));
    }

    @Test
    public void testSetTotalDaysOverLoanLimitForChild() {
        int numDaysOverdue = childMember.setTotalDaysOverLoanLimit(childMember.getLoans(), childMember);

        // For the Child, book one is 7 days and book two is 14 days overdue.
        assertThat(numDaysOverdue, equalTo(21));
    }
}
