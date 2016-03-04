package com.espaciounido.unadcalendar.model.parser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by raalzate on 23/02/2016.
 */
@Root
public class CurrentWeather {
    @Element(name = "Location")
    public String location;
    @Element(name = "Temperature")
    public String temperature;
}
