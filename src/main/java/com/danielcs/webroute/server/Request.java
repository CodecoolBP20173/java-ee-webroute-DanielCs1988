package com.danielcs.webroute.server;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

public interface Request {

    String getBody();
    <T> T getObjectFromBody(Class<T> type);
    String getParam(String key);
    Map<String, String> getParams();
    String getHeader(String key);
    Map<String, List<String>> getHeaders();
    InetSocketAddress getAddress();
    String getPath();
    String getFragment();
    String getQueryParam(String key);
    Map<String, String> getQueryParams();

}