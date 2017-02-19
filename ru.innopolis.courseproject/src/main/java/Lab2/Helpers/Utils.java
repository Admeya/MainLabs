package Lab2.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Ирина on 18.02.2017.
 */
public class Utils {
     public static java.util.Date SQLDateToUtilDate(java.sql.Date dateBeforeFormat) throws ParseException {
        java.util.Date formatDate = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(dateBeforeFormat));
        return formatDate;
    }

    public static java.sql.Date UtilDateToSQLDate(java.util.Date dateBeforeFormat) throws ParseException {
        java.sql.Date formatDate = new java.sql.Date(dateBeforeFormat.getTime());
        return formatDate;
    }


}
