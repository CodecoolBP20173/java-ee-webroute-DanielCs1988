package com.danielcs.webroute.server;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebRoute {

    enum Method {
        GET, POST, PUT, DELETE
    }
    String path();
    Method method() default Method.GET;

}
