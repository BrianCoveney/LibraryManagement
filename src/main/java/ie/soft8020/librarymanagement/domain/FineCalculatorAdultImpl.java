package ie.soft8020.librarymanagement.domain;

import ie.soft8020.librarymanagement.util.Const;
import ie.soft8020.librarymanagement.util.DateUtilility;

public class FineCalculatorAdultImpl implements IFineCalculator {

    public double getDaysOverLoanLimit(Member member) {
        double daysOverLoanLimit = 0.0;
        // For each loan in the list
        for (Loan loan : member.getLoans()) {
            // We get the num of days books have been borrowed
            int daysOnLoan = DateUtilility.calculatePeriodBetweenDays(loan.getLoanDate(), loan.getReturnDate());

            if (daysOnLoan > Const.LoanLength.MAX_LENGTH_OF_DAYS_ADULT_CAN_BORROW) {
                daysOverLoanLimit += daysOnLoan - Const.LoanLength.MAX_LENGTH_OF_DAYS_ADULT_CAN_BORROW;
            }
        }
        return daysOverLoanLimit;
    }

    public double calculateFine(Member member,  double daysOverLoanLimit) {
        double fine = daysOverLoanLimit * Const.FineAccrued.FINE_VALUE;
        if (fine > 0.0) {
            return member.getFinesOutstanding() + fine;
        }
        return 0.0;
    }
}
