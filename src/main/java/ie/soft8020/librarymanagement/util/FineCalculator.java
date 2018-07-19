package ie.soft8020.librarymanagement.util;

import ie.soft8020.librarymanagement.domain.Adult;
import ie.soft8020.librarymanagement.domain.Child;
import ie.soft8020.librarymanagement.domain.Loan;
import ie.soft8020.librarymanagement.domain.Member;
import ie.soft8020.librarymanagement.util.Const.LoanLength;

// This class will work fine, but I'm moving the logic back to the domain objects for now
public class FineCalculator {

    public double calculateFine(Member member) {
        double daysOver = getDaysOverLimit(member);
        double currFine = member.getFinesOutstanding();
        double fine = daysOver * Const.FineAccrued.FINE_VALUE;
        return currFine + fine;
    }

    public int getDaysOnLoan(Member member) {
        int days = 0;
        for (Loan loan : member.getLoans()) {
            days = DateUtilility.calculatePeriodBetweenDays(loan.getLoanDate(), loan.getReturnDate());
        }
        return days;
    }

    public double getDaysOverLimit(Member member) {
        double daysOverLoanLimit = 0;
        int days = getDaysOnLoan(member);
        if(member instanceof Adult) {
            if (days > LoanLength.MAX_ADULT_DAYS) {
                daysOverLoanLimit += days - LoanLength.MAX_ADULT_DAYS;
            }
        }
        if(member instanceof Child) {
            if (days > LoanLength.MAX_CHILD_DAYS) {
                daysOverLoanLimit += days - LoanLength.MAX_CHILD_DAYS;
            }
        }
        return daysOverLoanLimit;
    }
}
