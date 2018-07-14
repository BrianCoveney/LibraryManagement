package ie.soft8020.librarymanagement.util;

public final class Const {

	public static class LoanLimit {
		public static final int MAX_NUMBER_OF_BOOKS_FOR_ADULT = 4;
		public static final int MAX_NUMBER_OF_BOOKS_FOR_CHILD = 2;
	}

	public static class LoanLength {
		public static final int MAX_LENGTH_OF_DAYS_ADULT_CAN_BORROW = 14;
		public static final int MAX_LENGTH_OF_DAYS_CHILD_CAN_BORROW = 7;
	}

	public static class FineAccrued {
		public static final double FINE_VALUE = 0.25;
	}
}
