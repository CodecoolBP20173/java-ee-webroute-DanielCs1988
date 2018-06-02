package com.danielcs.webroute;

import com.danielcs.webroute.server.BasicServer;
import com.danielcs.webroute.server.Server;

public class Run {

    public static void main(String[] args) {
        Server server = new BasicServer(8000, "com.danielcs.webroute.controllers");
        server.start();
    }

}
