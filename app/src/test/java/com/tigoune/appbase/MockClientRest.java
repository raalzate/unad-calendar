package com.espaciounido.unadcalendar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by raalzate on 17/02/2016.
 */
public class MockClientRest implements Client {
    private String resource;
    public MockClientRest(String resource){
        this.resource = resource;
    }

    @Override
    public Response execute(Request request) throws IOException {
        String responseString = loadJSONMock(this.resource);
        return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST,
                new TypedByteArray("application/json", responseString.getBytes()));
    }

    public String loadJSONMock(String mock) {
        String json = null;
        try {
            InputStream is = this.getClass().getClassLoader()
                    .getResourceAsStream(mock);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }



}