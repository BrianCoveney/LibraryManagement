package ie.soft8020.librarymanagement.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Const {

	public static class LoanLimit {
		public static final int MAX_NUMBER_OF_BOOKS_FOR_ADULT = 4;
		public static final int MAX_NUMBER_OF_BOOKS_FOR_CHILD = 2;
	}

	public static class LoanLength {
		public static final int MAX_ADULT_DAYS = 14;
		public static final int MAX_CHILD_DAYS = 7;
	}

	public static class FineAccrued {
		public static final double FINE_VALUE = 0.25;
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
