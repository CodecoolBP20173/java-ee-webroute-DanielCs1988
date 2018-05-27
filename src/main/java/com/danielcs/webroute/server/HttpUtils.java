package com.danielcs.webroute.server;

import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.util.Scanner;

public class HttpUtils {

    public static void render(HttpExchange http, String response) throws IOException {
        http.sendResponseHeaders(200, response.getBytes().length);
        OutputStream out = http.getResponseBody();
        out.write(response.getBytes());
        out.close();
    }

    public static String getRequestBody(HttpExchange http) {
        InputStream in = http.getRequestBody();
        Scanner s = new Scanner(in).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
