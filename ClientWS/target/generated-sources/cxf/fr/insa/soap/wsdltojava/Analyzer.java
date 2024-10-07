package fr.insa.soap.wsdltojava;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.4.2
 * 2024-10-07T10:50:13.234+02:00
 * Generated source version: 3.4.2
 *
 */
@WebServiceClient(name = "analyzer",
                  wsdlLocation = "file:/home/vasseu/eclipse-workspace/ClientWS/src/main/resources/wsdl/analyser.wsdl",
                  targetNamespace = "http://soap.insa.fr/")
public class Analyzer extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://soap.insa.fr/", "analyzer");
    public final static QName AnalyserChaineWSPort = new QName("http://soap.insa.fr/", "AnalyserChaineWSPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/home/vasseu/eclipse-workspace/ClientWS/src/main/resources/wsdl/analyser.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(Analyzer.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:/home/vasseu/eclipse-workspace/ClientWS/src/main/resources/wsdl/analyser.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public Analyzer(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public Analyzer(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Analyzer() {
        super(WSDL_LOCATION, SERVICE);
    }

    public Analyzer(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public Analyzer(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public Analyzer(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns AnalyserChaineWS
     */
    @WebEndpoint(name = "AnalyserChaineWSPort")
    public AnalyserChaineWS getAnalyserChaineWSPort() {
        return super.getPort(AnalyserChaineWSPort, AnalyserChaineWS.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AnalyserChaineWS
     */
    @WebEndpoint(name = "AnalyserChaineWSPort")
    public AnalyserChaineWS getAnalyserChaineWSPort(WebServiceFeature... features) {
        return super.getPort(AnalyserChaineWSPort, AnalyserChaineWS.class, features);
    }

}
