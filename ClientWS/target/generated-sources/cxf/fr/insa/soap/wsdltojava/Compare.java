
package fr.insa.soap.wsdltojava;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java pour compare complex type.
 * 
 * &lt;p&gt;Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="compare"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="chain" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "compare", propOrder = {
    "chain"
})
public class Compare {

    protected String chain;

    /**
     * Obtient la valeur de la propriété chain.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChain() {
        return chain;
    }

    /**
     * Définit la valeur de la propriété chain.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChain(String value) {
        this.chain = value;
    }

}
