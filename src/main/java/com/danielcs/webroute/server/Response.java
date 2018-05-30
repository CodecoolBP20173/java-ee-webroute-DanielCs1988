package com.danielcs.webroute.server;

import java.io.IOException;

public interface Response {

    void sendString(String content) throws IOException;
    void sendObject(Object object) throws IOException;
    void addHeader(String key, String value);

}
