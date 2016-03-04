package com.espaciounido.unadcalendar.model.parser;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by raalzate on 23/02/2016.
 */
@Root
public class GetWeather  {

    public static final String PREFIX = "web";

    public GetWeather(){}

    @Element(name = PREFIX+":CityName")
    private String cityName;

    @Element(name = PREFIX+":CountryName")
    private String countryName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


}
