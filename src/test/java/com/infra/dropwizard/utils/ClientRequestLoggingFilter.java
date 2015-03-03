package com.infra.dropwizard.utils;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by mileslux on 3/2/15.
 */
public class ClientRequestLoggingFilter implements ClientRequestFilter {

    @Override
    public void filter(ClientRequestContext reqCtx) throws IOException {
        System.out.println("");
        System.out.println("uri: " + reqCtx.getUri());
        System.out.println("date: " + reqCtx.getDate());
        System.out.println("method: " + reqCtx.getMethod());
        System.out.println("headers:");
        for (Map.Entry<String, List<String>> header : reqCtx.getStringHeaders()
                .entrySet()) {
            System.out.print("\t" + header.getKey() + " :");
            for (String value : header.getValue()) {
                System.out.print(value + ", ");
            }
            System.out.print("\n");
        }
        System.out.println("media-type: " + reqCtx.getMediaType());
    }
}
