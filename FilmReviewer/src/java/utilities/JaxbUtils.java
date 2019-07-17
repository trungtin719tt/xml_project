/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.File;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author ASUS
 */
public class JaxbUtils implements Serializable{
    
    
//    public static String marshallToString(Object object){
//        try {
//            JAXBContext ctx = JAXBContext.newInstance(object.getClass());
//            Marshaller mar = ctx.createMarshaller();
//            mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            StringWriter sw = new StringWriter();
//            mar.marshal(object, sw);
//            System.out.println(sw.toString());
//            return sw.toString();
//        } catch (JAXBException e) {
//            Logger.getLogger(JaxbUtils.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return null;
//    }
//    
//    public static Source unmarshallSource(String xmlCrawlSource){
//        try {
//            JAXBContext ctx = JAXBContext.newInstance(Source.class);
//            Unmarshaller u = ctx.createUnmarshaller();
//            File f = new File(xmlCrawlSource);
//            Source source = (Source) u.unmarshal(f);
//            return source;
//        } catch (JAXBException ex) {
//            Logger.getLogger(JaxbUtils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    
    public static <T> T parseToObject(T obj, String filePath) throws JAXBException{
        JAXBContext jc = JAXBContext.newInstance(obj.getClass());
        Unmarshaller u = jc.createUnmarshaller();
        
        obj = (T) u.unmarshal(new File(filePath));
        return obj;
    }
    
    public static <T> T parseSourceToObject(T obj, javax.xml.transform.Source source) throws JAXBException{
        JAXBContext jc = JAXBContext.newInstance(obj.getClass());
        Unmarshaller u = jc.createUnmarshaller();
        
        obj = (T) u.unmarshal(source);
        return obj;
    }
    
    public static <T> String parseToXMLString(T obj) throws JAXBException{
        JAXBContext jc = JAXBContext.newInstance(obj.getClass());
        Marshaller mar = jc.createMarshaller();
        StringWriter sw  = new StringWriter();
        mar.marshal(obj, sw);
        
        return sw.toString();
    }
    
    public static <T> T parseXMLStringToObject(T obj, String xmlString) throws JAXBException{
        JAXBContext ctx = JAXBContext.newInstance(obj.getClass());
        Unmarshaller u = ctx.createUnmarshaller();
        StringReader reader = new StringReader(xmlString);
        obj = (T) u.unmarshal(reader);
        return obj;
    }
}
