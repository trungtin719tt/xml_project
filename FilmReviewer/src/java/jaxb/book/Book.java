
package jaxb.book;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Author" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Score" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="review" type="{http://trungtin719.tt/2019/xml}Review" maxOccurs="unbounded" minOccurs="0"/>
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
    "name",
    "author",
    "score",
    "review"
})
@XmlRootElement(name = "Book")
public class Book {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Author", required = true)
    protected String author;
    @XmlElement(name = "Score")
    protected float score;
    protected List<Review> review;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthor(String value) {
        this.author = value;
    }

    /**
     * Gets the value of the score property.
     * 
     */
    public float getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     * 
     */
    public void setScore(float value) {
        this.score = value;
    }

    /**
     * Gets the value of the review property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the review property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReview().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Review }
     * 
     * 
     */
    public List<Review> getReview() {
        if (review == null) {
            review = new ArrayList<Review>();
        }
        return this.review;
    }

}
