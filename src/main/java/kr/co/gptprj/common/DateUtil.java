package kr.co.gptprj.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	
	public static String getCurDate(LocalDateTime localDateTime) {
		String localDateTimeFormatForDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		return localDateTimeFormatForDate;
	}

	public static String getCurTime(LocalDateTime localDateTime) {
		String localDateTimeFormatForDate = localDateTime.format(DateTimeFormatter.ofPattern("HHmmss"));
		return localDateTimeFormatForDate;
	}

}
