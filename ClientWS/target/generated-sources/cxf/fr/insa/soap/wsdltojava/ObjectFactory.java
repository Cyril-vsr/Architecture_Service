
package fr.insa.soap.wsdltojava;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.insa.soap.wsdltojava package. 
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Compare_QNAME = new QName("http://soap.insa.fr/", "compare");
    private final static QName _CompareResponse_QNAME = new QName("http://soap.insa.fr/", "compareResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.insa.soap.wsdltojava
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Compare }
     * 
     */
    public Compare createCompare() {
        return new Compare();
    }

    /**
     * Create an instance of {@link CompareResponse }
     * 
     */
    public CompareResponse createCompareResponse() {
        return new CompareResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Compare }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Compare }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.insa.fr/", name = "compare")
    public JAXBElement<Compare> createCompare(Compare value) {
        return new JAXBElement<Compare>(_Compare_QNAME, Compare.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CompareResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CompareResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.insa.fr/", name = "compareResponse")
    public JAXBElement<CompareResponse> createCompareResponse(CompareResponse value) {
        return new JAXBElement<CompareResponse>(_CompareResponse_QNAME, CompareResponse.class, null, value);
    }

}
