package com.espaciounido.unadcalendar.soap;


import com.squareup.okhttp.OkHttpClient;
import com.espaciounido.unadcalendar.soap.envelope.RequestEnvelope;
import com.espaciounido.unadcalendar.soap.envelope.ResponseEnvelope;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.SimpleXMLConverter;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by raalzate on 14/12/2015.
 */
public class SoapClientPlaceHolder {

    public static final String API_BASE_URL = "http://www.webservicex.com";
    private static SoapClientPlaceHolder mSoapClientPlaceHolder;
    private final SoapApi service;

    private SoapClientPlaceHolder(Client client) {
        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_BASE_URL)
                .setClient(client)
                .setConverter(new SimpleXMLConverter(serializer))
                .setLogLevel(RestAdapter.LogLevel.FULL) //<- cambiar a debug
                .build();
        service = restAdapter.create(SoapApi.class);
    }



    public static SoapClientPlaceHolder provideApiClient() {
        if(mSoapClientPlaceHolder == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            mSoapClientPlaceHolder = new SoapClientPlaceHolder(new OkClient(okHttpClient));
        }
        return mSoapClientPlaceHolder;
    }

    public static SoapClientPlaceHolder provideApiClient(Client client) {
        return new SoapClientPlaceHolder(client);
    }


    public SoapApi getService() {
        return service;
    }


    public enum KeySoapClientPlaceHolder {


        MainTitle("main_title");

        private final String name;

        KeySoapClientPlaceHolder(String name){
            this.name = name;
        }

        @Override
        public String toString(){
            return name;
        }
    }

    public interface SoapApi {
        @Headers({
                "Content-Type: application/soap+xml",
                "Accept-Charset: utf-8"
        })
        @POST("/globalweather.asmx?WSDL")
        void getWeather(@Body RequestEnvelope body, Callback<ResponseEnvelope> cb);
    }
}
