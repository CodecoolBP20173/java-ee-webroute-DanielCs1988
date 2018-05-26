package com.danielcs.webroute.server;

import com.danielcs.webroute.controllers.TestController;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Set;

public class BasicServer implements Server {

    private final int PORT;
    private final String CONTROLLERS_PATH;

    public BasicServer(int port, String controllersPath) {
        this.PORT = port;
        this.CONTROLLERS_PATH = controllersPath;
    }

    private Set<Method> scanClassPath() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(CONTROLLERS_PATH))
                .setScanners(new MethodAnnotationsScanner())
        );
        return reflections.getMethodsAnnotatedWith(WebRoute.class);
    }

    private void setupContext(HttpServer server) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Set<Method> controllers = scanClassPath();
        for (Method controller : controllers) {
            WebRoute route = controller.getAnnotation(WebRoute.class);
            HttpHandler handler = (HttpHandler) controller.getDeclaringClass().getConstructor().newInstance();
            server.createContext(route.path(), handler);
        }
    }

    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            setupContext(server);
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            System.out.println("Failed to open connection!");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server s = new BasicServer(8000, "com.danielcs.webroute.controllers");
        s.start();
    }
}
