package com.example.tostudy.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
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

    static final String EMAIL_PATTERN = "\"^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$\"";

    public static boolean isEmailValid(String email){
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();

    }

    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT).replace("\n","");
    }

    public static Bitmap redimensionarImagen(Bitmap mBitmap){
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth =  0.3F;
        float scaleHeight = 0.3F;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }
}
