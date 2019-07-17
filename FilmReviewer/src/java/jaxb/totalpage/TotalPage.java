
package jaxb.totalpage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumOfPage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "numOfPage"
})
@XmlRootElement(name = "TotalPage")
public class TotalPage {

    @XmlElement(name = "NumOfPage")
    protected int numOfPage;

    /**
     * Gets the value of the numOfPage property.
     * 
     */
    public int getNumOfPage() {
        return numOfPage;
    }

    /**
     * Sets the value of the numOfPage property.
     * 
     */
    public void setNumOfPage(int value) {
        this.numOfPage = value;
    }

}
