package ie.soft8020.librarymanagement.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class DateUtilility {

	public static int calculatePeriodBetweenYears(Date inputDate) {
		LocalDate now = LocalDate.now();

		if (inputDate != null && now != null) {

			LocalDate date = convertDateToLocalDate(inputDate);

			if (date.isAfter(now))
				throw new IllegalArgumentException("You cannot be born in the future!");

			return Period.between(date, now).getYears();
		} else {
			return 0;
		}
	}

	public static int calculatePeriodBetweenDays(Date inputDateOne, Date inputDateTwo) {
		if (inputDateOne != null && inputDateTwo != null) {

			LocalDate dateOne = convertDateToLocalDate(inputDateOne);
			LocalDate dateTwo = convertDateToLocalDate(inputDateTwo);

			if (dateOne.isAfter(dateTwo))
				throw new IllegalArgumentException("You cannot borrow books in the future!");

			return Period.between(dateOne, dateTwo).getDays();
		} else {
			return 0;
		}
	}

	public static LocalDate convertDateToLocalDate(Date date) {
		LocalDate localDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate;
	}


	public static Date parseStringToDate(String input) {
		Date newDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			newDate = dateFormat.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	public static Date asDate(LocalDate localDate) {
	    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
}
