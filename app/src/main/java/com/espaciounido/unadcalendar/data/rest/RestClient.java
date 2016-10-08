package com.espaciounido.unadcalendar.data.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MyMac on 5/09/16.
 */
public class RestClient {

    private static final String URL_BASE_UNADCALENDAR = "http://services-rauloko.rhcloud.com/ws/unadcalendar/";
    private static final long TIMEOUT_IN_SECONDS = 10;

    private UnadCalendarApi unadCalendarApi;

    public RestClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                // .addInterceptor(logging)
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();

        unadCalendarApi = new Retrofit.Builder()
                .baseUrl(URL_BASE_UNADCALENDAR)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(UnadCalendarApi.class);
    }

    public UnadCalendarApi getUnadCalendarApi() {
        return unadCalendarApi;
    }
}
