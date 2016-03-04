package com.espaciounido.unadcalendar.soap.body;

import com.espaciounido.unadcalendar.model.parser.GetWeather;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by raalzate on 23/02/2016.
 */
@Root(name = "S:Body", strict = false)
public class RequestGetWeatherBody {

    @Element(name = GetWeather.PREFIX+":GetWeather")
    private GetWeather getWeather;

    public RequestGetWeatherBody() {}

    public GetWeather getWeather() {
        return getWeather;
    }

    public void setWeather(GetWeather node) {
        this.getWeather = node;
    }
}
