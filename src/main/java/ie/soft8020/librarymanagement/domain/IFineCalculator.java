package ie.soft8020.librarymanagement.domain;

public interface IFineCalculator {
    double calculateFine(Member member, double daysOverLoanLimit);
    double getDaysOverLoanLimit(Member member);
}
