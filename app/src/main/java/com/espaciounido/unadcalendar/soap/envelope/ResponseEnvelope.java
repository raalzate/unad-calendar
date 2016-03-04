package com.espaciounido.unadcalendar.soap.envelope;

import com.espaciounido.unadcalendar.soap.body.ResponseGetWeatherResponseBody;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by raalzate on 23/02/2016.
 */
@Root(name = "soap:Envelope")
@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi"),
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema", prefix = "xsd"),
        @Namespace(prefix = "soap", reference = "http://www.w3.org/2003/05/soap-envelope")
})
public class ResponseEnvelope {

    @Element(name = "Body")
    @Namespace(prefix= "soap")
    private ResponseGetWeatherResponseBody body;
    public ResponseGetWeatherResponseBody getBody() {
        return body;
    }
    public void setBody(ResponseGetWeatherResponseBody body) {
        this.body = body;
    }
}
