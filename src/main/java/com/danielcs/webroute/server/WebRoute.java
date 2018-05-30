package com.danielcs.webroute.server;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebRoute {

    enum Method {
        GET, POST, PUT, DELETE
    }

    enum ParseMode {
        NONE, JSON, WRAP
    }

    String path();
    Method method() default Method.GET;
    ParseMode params() default ParseMode.WRAP;
    Class input() default Object.class;

}
