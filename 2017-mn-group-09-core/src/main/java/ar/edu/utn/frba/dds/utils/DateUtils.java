package ar.edu.utn.frba.dds.utils;

import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by pablomorrone on 11/4/17.
 */
public class DateUtils {

    private static final String FORMAT = "dd/MM/yyyy";
    private static final String FORMATWeb = "yyyy-MM-dd";

    public static LocalDate getDateFromString(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
        LocalDate d = LocalDate.parse(date,formatter);
        return d;
    }

    public static LocalDate getDateFromStringWeb(String date){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATWeb);
            LocalDate d = LocalDate.parse(date,formatter);
            return d;
        }catch (Exception ex){
            return null;
        }
    }

    public static String getStringFromDate(LocalDate date){
        return dateFormat(date,FORMAT);
    }

    public static String getStringFromWebDate(LocalDate date){
        return dateFormat(date,FORMATWeb);
    }

    @NotNull
    public static String dateFormat(@NotNull LocalDate date, @NotNull String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    public static Boolean isBetween(LocalDate from, LocalDate to, LocalDate date){
        Boolean ret = true;

        if (from != null) ret = ret && (date.isAfter(from)|| date.equals(from));

        if (to != null) ret = ret && (date.isBefore(to)|| date.equals(to));

        return ret;
    }

    public static LocalDate firstDate(){
        return LocalDate.of(1970,01,1);
    }

}
