package com.danielcs.webroute.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServer implements HttpHandler {

    private Map<String, Handler> handlers = new HashMap<>();

    public DispatcherServer(Map<String, Method> methods) {
        initHandlers(methods);
    }

    private void initHandlers(Map<String, Method> methods) {
        for (String httpMethod : methods.keySet()) {
            try {
                Method method = methods.get(httpMethod);
                Object caller = method
                        .getDeclaringClass()
                        .getConstructor()
                        .newInstance();
                handlers.put(httpMethod, new Handler(caller, method));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public void handle(HttpExchange http) throws IOException {
        String method = http.getRequestMethod();
        handlers.get(method).handleRequest(http);
    }

    private final class Handler {

        private final Object caller;
        private final Method method;

        public Handler(Object caller, Method method) {
            this.caller = caller;
            this.method = method;
        }

        public void handleRequest(HttpExchange http) {
            try {
                method.invoke(caller, http);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
