package com.danielcs.webroute.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;

public class BasicServer implements Server {

    private final int PORT;
    private final String CONTROLLERS_PATH;
    private int maxThreads = 100;

    public BasicServer(int port, String controllersPath) {
        this.PORT = port;
        this.CONTROLLERS_PATH = controllersPath;
    }

    public BasicServer(int port, String controllersPath, int maxThreads) {
        this(port, controllersPath);
        this.maxThreads = maxThreads;
    }

    private Set<Method> scanClassPath() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(CONTROLLERS_PATH))
                .setScanners(new MethodAnnotationsScanner())
        );
        return reflections.getMethodsAnnotatedWith(WebRoute.class);
    }

    private void setupContext(HttpServer server) {
        Set<Method> controllers = scanClassPath();
        Map<String, Map<String, Method>> pathMappings = new HashMap<>();
        for (Method controller : controllers) {
            WebRoute route = controller.getAnnotation(WebRoute.class);
            String path = route.path();
            if (pathMappings.containsKey(path)) {
                pathMappings.get(path).put(route.method().toString(), controller);
            } else {
                Map<String, Method> methodMappings = new HashMap<>();
                methodMappings.put(route.method().toString(), controller);
                pathMappings.put(path, methodMappings);
            }
        }
        for (String path : pathMappings.keySet()) {
            String pathURI = path.replaceAll("<.*", "");
            HttpHandler dispatcher = new DispatcherServer(pathMappings.get(path), path);
            server.createContext(pathURI, dispatcher);
        }
    }

    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            setupContext(server);
            server.setExecutor(Executors.newFixedThreadPool(maxThreads));
            server.start();
        } catch (IOException e) {
            System.out.println("Failed to open connection!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server s = new BasicServer(8000, "com.danielcs.webroute.controllers");
        s.start();
    }
}
