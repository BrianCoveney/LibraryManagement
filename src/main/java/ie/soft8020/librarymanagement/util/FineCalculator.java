package ie.soft8020.librarymanagement.util;

import ie.soft8020.librarymanagement.domain.Adult;
import ie.soft8020.librarymanagement.domain.Child;
import ie.soft8020.librarymanagement.domain.Loan;
import ie.soft8020.librarymanagement.domain.Member;

public class FineCalculator {

    public double getDaysOverLoanLimit(Member member) {
        double totalDaysOverLoanLimit = 0;
        // For each loan in the list
        for (Loan loan : member.getLoans()) {
            // We get the num of days books have been borrowed
            int daysOnLoan = DateUtilility.calculatePeriodBetweenDays(loan.getLoanDate(), loan.getReturnDate());

            // We check weather the member is an Adult or Child.
            // When the daysOnLoan are over the members' limit, we increment a value and pass it to daysPastLoanLimit.
            if (member instanceof Adult) {
                if (daysOnLoan > Const.LoanLength.MAX_LENGTH_OF_DAYS_ADULT_CAN_BORROW) {
                    totalDaysOverLoanLimit += daysOnLoan - Const.LoanLength.MAX_LENGTH_OF_DAYS_ADULT_CAN_BORROW;
                }
            } else if (member instanceof Child) {
                if (daysOnLoan > Const.LoanLength.MAX_LENGTH_OF_DAYS_CHILD_CAN_BORROW) {
                    totalDaysOverLoanLimit += daysOnLoan - Const.LoanLength.MAX_LENGTH_OF_DAYS_CHILD_CAN_BORROW;
                }
            }
        }

        return totalDaysOverLoanLimit;
    }

    public double calculateFine(Member member,  double daysOverLoanLimit) {
        double fine = daysOverLoanLimit * Const.FineAccrued.FINE_VALUE;
        double currentFine = member.getFinesOutstanding();
        if (fine > 0.0) {
            return currentFine + fine;
        }
        return currentFine;
    }
}
