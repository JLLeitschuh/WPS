
package org.n52.wps.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

public class GetCapabilitiesKVPTests {

    private static String url;

    @BeforeClass
    public static void beforeClass() {
        url = AllTestsIT.getURL();
    }

    @Test
    public void complete() throws ParserConfigurationException, SAXException, IOException {
        String response = GetClient.sendRequest(url, "Service=WPS&Request=GetCapabilities&Version=1.0.0");

        assertThat(AllTestsIT.parseXML(response), is(not(nullValue())));
        assertThat(response, response, not(containsString("ExceptionReport")));
        
        assertThat(response, response, containsString("<wps:Capabilities"));
        assertThat(response, response, containsString("<ows:Operation name=\"Execute\">"));
        assertThat(response, response, containsString("<ows:ServiceType>WPS</ows:ServiceType>"));
    }

    @Test
    public void missingRequestParameter() throws IOException,
            ParserConfigurationException,
            SAXException {
        String response = GetClient.sendRequest(url, "Service=WPS");

        assertThat(AllTestsIT.parseXML(response), is(not(nullValue())));
        assertThat(response, response, containsString("ExceptionReport"));
        assertThat(response, response, containsString("exceptionCode=\"MissingParameterValue\""));
        
        assertThat(response, response, not(containsString("<wps:Capabilities")));
    }
    
    @Test
    public void missingServiceParameter() throws IOException,
            ParserConfigurationException,
            SAXException {
        String response = GetClient.sendRequest(url, "Request=GetCapabilities&Version=1.0.0");

        assertThat(AllTestsIT.parseXML(response), is(not(nullValue())));
        assertThat(response, response, containsString("ExceptionReport"));
        assertThat(response, response, containsString("exceptionCode=\"MissingParameterValue\""));
    }

    @Test
    public void noVersionParameter() throws ParserConfigurationException,
            SAXException,
            IOException {
        String response = GetClient.sendRequest(url, "Service=WPS&Request=GetCapabilities");

        assertThat(AllTestsIT.parseXML(response), is(not(nullValue())));
        assertThat(response, response, not(containsString("ExceptionReport")));

        assertThat(response, response, containsString("<wps:Capabilities"));
        assertThat(response, response, containsString("<ows:Operation name=\"Execute\">"));
        assertThat(response, response, containsString("<ows:ServiceType>WPS</ows:ServiceType>"));
    }

    @Test
    public void wrongVersion() throws ParserConfigurationException, SAXException, IOException {
        String response = GetClient.sendRequest(url, "Service=WPS&Request=GetCapabilities&Version=42.17");

        assertThat(AllTestsIT.parseXML(response), is(not(nullValue())));
        assertThat(response, response, containsString("ExceptionReport"));
        assertThat(response, response, containsString("InvalidParameterValue"));

        assertThat(response, response, not(containsString("<wps:Capabilities")));
    }
    
    @Test
    public void wrongServiceParameter() throws ParserConfigurationException, SAXException, IOException {
        String response = GetClient.sendRequest(url, "Service=HotDogStand&Request=GetCapabilities&Version=42.17");

        assertThat(AllTestsIT.parseXML(response), is(not(nullValue())));
        assertThat(response, response, containsString("ExceptionReport"));
        assertThat(response, response, containsString("exceptionCode=\"InvalidParameterValue\""));
    }
}
