package Lab2.Helpers;

import java.io.File;
import java.io.FileNotFoundException;
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

    private static void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
    }

    public static void delete(String nameFile) throws FileNotFoundException {
        exists(nameFile);
        new File(nameFile).delete();
    }


}
