package com.espaciounido.unadcalendar.soap.body;

import com.espaciounido.unadcalendar.model.parser.GetWeatherResponse;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by raalzate on 23/02/2016.
 */
@Root(name = "soap:Body", strict = false)
public class ResponseGetWeatherResponseBody {

    @Element(name="GetWeatherResponse")
    private GetWeatherResponse getWeatherResponse;

    public ResponseGetWeatherResponseBody() {}

    public GetWeatherResponse getWeatherResponse() {
        return getWeatherResponse;
    }

    public void setWeatherResponse(GetWeatherResponse node) {
        this.getWeatherResponse = node;
    }

}
