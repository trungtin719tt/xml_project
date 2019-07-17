
package jaxb.book;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Review complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Review">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReviewBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReviewContent" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Score" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="NumberOfLikes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Review", propOrder = {
    "reviewBy",
    "reviewContent",
    "score",
    "numberOfLikes"
})
public class Review {

    @XmlElement(name = "ReviewBy", required = true)
    protected String reviewBy;
    @XmlElement(name = "ReviewContent", required = true)
    protected String reviewContent;
    @XmlElement(name = "Score")
    protected Float score;
    @XmlElement(name = "NumberOfLikes")
    protected Integer numberOfLikes;

    /**
     * Gets the value of the reviewBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReviewBy() {
        return reviewBy;
    }

    /**
     * Sets the value of the reviewBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReviewBy(String value) {
        this.reviewBy = value;
    }

    /**
     * Gets the value of the reviewContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReviewContent() {
        return reviewContent;
    }

    /**
     * Sets the value of the reviewContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReviewContent(String value) {
        this.reviewContent = value;
    }

    /**
     * Gets the value of the score property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setScore(Float value) {
        this.score = value;
    }

    /**
     * Gets the value of the numberOfLikes property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfLikes() {
        return numberOfLikes;
    }

    /**
     * Sets the value of the numberOfLikes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfLikes(Integer value) {
        this.numberOfLikes = value;
    }

}
