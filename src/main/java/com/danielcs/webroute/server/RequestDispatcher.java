package com.danielcs.webroute.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

class RequestDispatcher implements HttpHandler {

    private Map<String, Handler> handlers = new HashMap<>();
    private String path;
    private HttpExchangeProcessor processor;
    private Gson converter = new Gson();

    RequestDispatcher(Map<String, Method> methods, String path) {
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

    private List<Object> extractPathVariables(HttpExchange http) {
        List<Object> vars = new ArrayList<>();
        String[] pathElements = path.split("/");
        pathElements = Arrays.copyOfRange(pathElements, 1, pathElements.length);
        String[] uriElements = http.getRequestURI().getPath().split("/");
        uriElements = Arrays.copyOfRange(uriElements, 1, uriElements.length);

        if (pathElements.length != uriElements.length) return null;

        for (int i = 0; i < pathElements.length; i++) {
            if (pathElements[i].matches("^<.+:int>$")) {
                vars.add(Integer.valueOf(uriElements[i]));
            } else if (pathElements[i].matches("^<.+>$")) {
                vars.add(uriElements[i]);
            } else if (!pathElements[i].equals(uriElements[i])) {
                return null;
            }
        }
        return vars;
    }

    private void handlePageNotFound(HttpExchange http) throws IOException {
        String resp = "<h1>The page you requested could not be found on the server.</h1>";
        http.sendResponseHeaders(404, resp.getBytes().length);
        OutputStream out = http.getResponseBody();
        out.write(resp.getBytes());
        out.close();
    }

    public void handle(HttpExchange http) throws IOException {
        String method = http.getRequestMethod();
        List<Object> pathVars = new ArrayList<>();
        if (path != null) {
            pathVars = extractPathVariables(http);
        }
        if (pathVars == null) {
            handlePageNotFound(http);
        } else {
            handlers.get(method).handleRequest(http, pathVars);
        }
    }

    private final class Handler {

        private final Object caller;
        private final Method method;
        private final WebRoute.ParseMode parseMode;
        private Class JsonType;

        Handler(Object caller, Method method) {
            this.caller = caller;
            this.method = method;
            this.parseMode = method.getAnnotation(WebRoute.class).params();
            if (parseMode != WebRoute.ParseMode.NONE && processor == null) {
                processor = new HttpExchangeProcessor(converter);
            }
            if (parseMode == WebRoute.ParseMode.JSON) {
                JsonType = method.getAnnotation(WebRoute.class).input();
            }
        }

        @SuppressWarnings("unchecked")
        void handleRequest(HttpExchange http, List<Object> args) {
            try {
                int numberOfArgs = (parseMode == WebRoute.ParseMode.NONE) ? 1 : 2;
                Object[] params = new Object[args.size() + numberOfArgs];

                switch (parseMode) {
                    case WRAP:
                        params[0] = processor.getRequest(http);
                        params[1] = processor.getResponse(http);
                        break;
                    case JSON:
                        params[0] = processor.getRequest(http).getObjectFromBody(JsonType);
                        params[1] = processor.getResponse(http);
                        break;
                    case NONE:
                        params[0] = http;
                }

                for (int i = 0; i < args.size(); i++) {
                    params[i + numberOfArgs] = args.get(i);
                }
                method.invoke(caller, params);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
