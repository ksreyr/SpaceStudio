package de.spaceStudio.client.util;

import com.badlogic.gdx.Net;

public final class RequestUtils {
    /**
     * Prepares the headers and other configurations
     *
     * @param url
     * @param payload
     * @param httpMethod
     * @return
     */
    public static Net.HttpRequest setupRequest(String url, String payload, String httpMethod) {
        Net.HttpRequest request = new Net.HttpRequest(httpMethod);
        request.setTimeOut(0);
        request.setUrl(url);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setContent(payload);
        return request;
    }

}
