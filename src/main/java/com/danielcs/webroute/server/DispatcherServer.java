package com.danielcs.webroute.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class DispatcherServer implements HttpHandler {

    private Map<String, Handler> handlers = new HashMap<>();
    private String path;

    public DispatcherServer(Map<String, Method> methods, String path) {
        initHandlers(methods);
        if (path.matches(".*/<.*>.*")) {
            this.path = path;
        }
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

    private List<String> extractPathVariables(HttpExchange http) {
        List<String> vars = new ArrayList<>();
        String[] pathElements = path.split("/");
        pathElements = Arrays.copyOfRange(pathElements, 1, pathElements.length);
        String[] uriElements = http.getRequestURI().getPath().split("/");
        uriElements = Arrays.copyOfRange(uriElements, 1, uriElements.length);

        if (pathElements.length != uriElements.length) return null;

        for (int i = 0; i < pathElements.length; i++) {
            if (pathElements[i].matches("^<.+>$")) {
                vars.add(uriElements[i]);
            } else if (!pathElements[i].equals(uriElements[i])) {
                return null;
            }
        }
        return vars;
    }

    public void handle(HttpExchange http) throws IOException {
        String method = http.getRequestMethod();
        List<String> pathVars = new ArrayList<>();
        if (path != null) {
            pathVars = extractPathVariables(http);
        }
        if (pathVars == null) return;
        handlers.get(method).handleRequest(http, pathVars);
    }

    private final class Handler {

        private final Object caller;
        private final Method method;

        public Handler(Object caller, Method method) {
            this.caller = caller;
            this.method = method;
        }

        public void handleRequest(HttpExchange http, List<String> args) {
            try {
                Object[] params = new Object[args.size() + 1];
                params[0] = http;
                for (int i = 0; i < args.size(); i++) {
                    params[i + 1] = args.get(i);
                }
                method.invoke(caller, params);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
