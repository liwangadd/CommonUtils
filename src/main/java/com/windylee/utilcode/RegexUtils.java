package com.windylee.utilcode;

public class RegexUtils {

    private static final String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    private static final String MOBILE_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8}$";

    



    private RegexUtils(){
        throw new UnsupportedOperationException("the RegexUtils cannot be initialed");
    }

}
