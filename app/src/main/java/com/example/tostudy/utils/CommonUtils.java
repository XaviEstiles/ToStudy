package com.example.tostudy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase no se podra herredar de ella por eso le ponemos final.
 */
public final class CommonUtils {
    // todos sus metodo seran staticos
    /**
     * Debe tener 0-9
     * Debe tener a-z
     * Debe tener A-Z
     * Lenght 8-20
     */

    static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$*?¡\\-_.])(?!.*\\s).{8,20}$";

    public static boolean isPasswordValid(String password){
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();

    }
}
