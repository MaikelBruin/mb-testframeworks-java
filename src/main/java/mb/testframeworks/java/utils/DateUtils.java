package mb.testframeworks.java.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static LocalDateTime ldtNextMonth() {
        LocalDateTime today = LocalDateTime.now();
        return today.plusMonths(1);
    }

    public static LocalDate ldLastMonth() {
        LocalDate today = LocalDate.now();
        return today.minusMonths(1);
    }

    public static LocalDate ldNextYear() {
        LocalDate today = LocalDate.now();
        return today.plusYears(1);
    }

    public static LocalDate ldNextMonth() {
        LocalDate today = LocalDate.now();
        return today.plusMonths(1);
    }
    public static LocalDateTime ldtNextYear() {
        LocalDateTime today = LocalDateTime.now();
        return today.plusYears(1);
    }

    public static Date createDate(int years, int months, int days) {
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.add(Calendar.YEAR, years);
        tempCalendar.add(Calendar.MONTH, months);
        tempCalendar.add(Calendar.DAY_OF_MONTH, days);
        return tempCalendar.getTime();
    }

}