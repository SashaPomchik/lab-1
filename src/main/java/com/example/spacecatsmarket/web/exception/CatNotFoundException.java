package com.example.spacecatsmarket.web.exception;

public class CatNotFoundException extends RuntimeException {

    private static final String SORRY_CAT_YOU_TRYING_TO_REACH_HAVE_NOT_LEFT_GREETING_FOR_YOU = "Sorry %s, that you trying to reach, haven't left greeting for you";

    public CatNotFoundException(String name) {
        super(String.format(SORRY_CAT_YOU_TRYING_TO_REACH_HAVE_NOT_LEFT_GREETING_FOR_YOU, name));
    }
}
