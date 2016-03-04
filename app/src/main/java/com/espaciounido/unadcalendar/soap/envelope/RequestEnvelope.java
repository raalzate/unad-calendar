package com.espaciounido.unadcalendar.soap.envelope;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by raalzate on 23/02/2016.
 */
@Root(name = "soapenv:Envelope")
@NamespaceList({
        @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/", prefix = "soapenv"),
        @Namespace(reference = "http://www.webserviceX.NET", prefix = "web")
})
public class RequestEnvelope {
    @Element(name = "soapenv:Body")
    private Object body;
    public Object getBody() {
        return body;
    }
    public void setBody(Object body) {
        this.body = body;
    }
}
