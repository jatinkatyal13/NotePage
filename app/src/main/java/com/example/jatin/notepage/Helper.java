package com.example.jatin.notepage;

import java.util.regex.Pattern;

/**
 * Created by jatin on 16/02/17.
 */

public final class Helper {

    public static String trim(String input){
        input = input.trim();
        input = input.replaceAll("^\\n+", "");
        input = input.replaceAll("\\n+$", "");
        return input;
    }

    public static boolean isEmail(String input){
        return Pattern
                .compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?")
                .matcher(input)
                .matches();
    }

}
