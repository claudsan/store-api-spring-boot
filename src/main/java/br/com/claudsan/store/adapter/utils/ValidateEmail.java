package br.com.claudsan.store.adapter.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateEmail {

    public static void valid(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches())
            throw new RuntimeException(String.format("Email %s is not valid!", email));
    }
}
