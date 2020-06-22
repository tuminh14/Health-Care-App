package thien.ntn.Health_are_app.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import thien.ntn.Health_are_app.config.Constants;

public final class RegisterValidation {
    public static boolean isDate(String strDate) {
        /*
         * Set preferred date format,
         * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
        SimpleDateFormat sdfrmt = new SimpleDateFormat(Constants.DATE_FORMAT);
        sdfrmt.setLenient(false);
        /* Create Date object
         * parse the string into date
         */
        try
        {
            Date javaDate = sdfrmt.parse(strDate);
            System.out.println(strDate+" is valid date format");
        }
        /* Date format is invalid */
        catch (ParseException e)
        {
            System.out.println(strDate+" is Invalid Date format");
            return false;
        }
        /* Return true if date format is valid */
        return true;
    }

    public static boolean isNumeric(String str) {
        return str.matches(Constants.REGEX.NUMERIC);
    }

    public static boolean isEmail(String str) {
        return str.matches(Constants.REGEX.EMAIL);
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 6 &&
                password.length() <= 100 ;
    }

    public static boolean isValidName(String name) {
        return name.length() >=2 &&
                name.length() <= 100 ;
    }
}
