package com.espaciounido.unadcalendar;

import com.espaciounido.unadcalendar.soap.body.RequestGetWeatherBody;
import com.espaciounido.unadcalendar.soap.envelope.ResponseEnvelope;
import com.espaciounido.unadcalendar.soap.envelope.RequestEnvelope;
import com.espaciounido.unadcalendar.soap.SoapClientPlaceHolder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static junit.framework.Assert.assertEquals;

/**
 * Created by raalzate on 23/02/2016.
 */
public class SoapClientTest {

    private static final long TIMEOUT_RESPONSE = 500;
    private boolean isSuccess;
    private GetWeatherResponse getWeatherResponse;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void valid_GetWeatherResult_success() {

        GetWeather getWeather = new GetWeather();
        getWeather.setCityName("BOGOTA");
        getWeather.setCountryName("COLOMBIA");

        RequestGetWeatherBody body = new RequestGetWeatherBody();
        body.setWeather(getWeather);

        RequestEnvelope requestEnvelope = new RequestEnvelope();
        requestEnvelope.setBody(body);

         SoapClientPlaceHolder.provideApiClient(new MockClient("getWeatherResponse.xml"))
                .getService().getWeather(requestEnvelope, getCallBackGetWeather());

        waitEnds(); //realiza la espera del procesador

        assertEquals(true, isSuccess);

    }

    @Test public void verify_Parse_XMLtoPOJO() {
        String responseTest = "" +
                "<?xml version=\"1.0\" encoding=\"utf-16\"?>" +
                "<CurrentWeather>" +
                "  <Location>Bogota / Eldorado, Colombia (SKBO) 04-43N 074-09W 2548M</Location>" +
                "  <Temperature> 66 F (19 C)</Temperature>" +
                "</CurrentWeather>";

        Serializer serializer = new Persister();
        Reader reader = new StringReader(responseTest);
        try {
            CurrentWeather currentWeather = serializer.read(CurrentWeather.class, reader, false);
            assertEquals(currentWeather.temperature, " 66 F (19 C)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test public void verify_GetWeatherResult_Location() {

        GetWeather getWeather = new GetWeather();
        getWeather.setCityName("BOGOTA");
        getWeather.setCountryName("COLOMBIA");

        RequestGetWeatherBody body = new RequestGetWeatherBody();
        body.setWeather(getWeather);

        RequestEnvelope requestEnvelope = new RequestEnvelope();
        requestEnvelope.setBody(body);

        SoapClientPlaceHolder.provideApiClient(new MockClient("getWeatherResponse.xml"))
                .getService().getWeather(requestEnvelope, getCallBackGetWeather());

        waitEnds(); //realiza la espera del procesador

        //valida
        Serializer serializer = new Persister();
        Reader reader = new StringReader(getWeatherResponse.getGetWeatherResult());
        CurrentWeather currentWeather;
        try {
            currentWeather = serializer.read(CurrentWeather.class, reader, false);
            assertEquals(currentWeather.location, "Bogota / Eldorado, Colombia (SKBO) 04-43N 074-09W 2548M");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Callback<ResponseEnvelope> getCallBackGetWeather(){
        return new Callback<ResponseEnvelope>() {
            @Override
            public void success(ResponseEnvelope responseEnvelope, Response response) {
                isSuccess = true;
                getWeatherResponse = responseEnvelope.getBody().getWeatherResponse();
            }

            @Override
            public void failure(RetrofitError error) {
                isSuccess = false;
            }
        };
    }

    private void waitEnds() {
        try {
            Thread.sleep(TIMEOUT_RESPONSE);//tiempo prodente de procesamiento
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
