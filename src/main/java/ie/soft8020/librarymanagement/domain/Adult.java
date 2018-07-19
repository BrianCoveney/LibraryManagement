package ie.soft8020.librarymanagement.domain;

import ie.soft8020.librarymanagement.util.Const;
import ie.soft8020.librarymanagement.util.DateUtilility;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class Adult extends Member {

	private int loanLimit;
	private int loanLength;

	public Adult(String name, Date dateOfBirth) {
		super(name, dateOfBirth);
		setLoanLimit(Const.LoanLimit.MAX_NUMBER_OF_BOOKS_FOR_ADULT);
		setLoanLength(Const.LoanLength.MAX_ADULT_DAYS);
	}

	@Override
	public int getLoanLimit() {
		return loanLimit;
	}

	@Override
	public void setLoanLimit(int loanLimit) {
		this.loanLimit = loanLimit;
	}

	@Override
	public int getLoanLength() {
		return loanLength;
	}

	@Override
	public void setLoanLength(int loanLength) {
		this.loanLength = loanLength;
	}

	@Override
    public void calculateFine(Member member) {
        double daysOver = getDaysOverLimit();
        double currFine = member.getFinesOutstanding();
        double fine = daysOver * Const.FineAccrued.FINE_VALUE;
        setFinesOutstanding(currFine + fine);
    }

    private int getDaysOnLoan() {
        int days = 0;
        for (Loan loan : getLoans()) {
            days = DateUtilility.calculatePeriodBetweenDays(loan.getLoanDate(), loan.getReturnDate());
        }
        return days;
    }

    private double getDaysOverLimit() {
        double daysOverLoanLimit = 0;
        int days = getDaysOnLoan();

        if (days > Const.LoanLength.MAX_ADULT_DAYS) {
            daysOverLoanLimit += days - Const.LoanLength.MAX_ADULT_DAYS;
        }

        return daysOverLoanLimit;
    }


	@Override
	public String toString() {
		String out = "Adult [memberID=" + getMemberID() + ", name=" + getName() + ", address=" + getAddress()
				+ ", dateOfBirth=" + getDateOfBirth() + ", loanLimit=" + getLoanLimit() + ", loanLength="
				+ getLoanLength() + ", finesOutstanding=" + getFinesOutstanding() + "\n"
				+ "books=[" + getListToString(getBooks()) + "]]\n"
				+ "loan=[" + getListToString(getLoans()) + "]]";
		return out;
	}

	private String getListToString(List<?> list) {
		return list.stream().map(e -> e.toString()).collect(Collectors.joining(","));
	}



}
