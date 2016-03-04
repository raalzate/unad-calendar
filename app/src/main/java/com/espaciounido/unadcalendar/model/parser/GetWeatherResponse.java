package com.espaciounido.unadcalendar.model.parser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by raalzate on 23/02/2016.
 */
@Root
public  class GetWeatherResponse {

    public static final String ROOT_NAME = "GetWeatherResponse";

    public GetWeatherResponse(){}

    @Element(name = "GetWeatherResult", required = false)
    private String getWeatherResult;

    public String getGetWeatherResult() {
        return getWeatherResult;
    }

    public void setGetWeatherResult(String getWeatherResult) {
        this.getWeatherResult = getWeatherResult;
    }
}
