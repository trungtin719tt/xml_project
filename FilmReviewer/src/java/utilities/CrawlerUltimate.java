/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author ASUS
 */
public class CrawlerUltimate {
    public static DOMResult crawl(String configPath, String xslPath) throws FileNotFoundException, TransformerConfigurationException, TransformerException{
        StreamSource xlsCate = new StreamSource(xslPath);
        InputStream is = new FileInputStream(configPath);
        System.out.println(is.toString());
        
        TransformerFactory factory  = TransformerFactory.newInstance();
        DOMResult domRs = new DOMResult();
        UltimateURIResolver resolver = new UltimateURIResolver();
        
        factory.setURIResolver(resolver);
        Transformer transformer = factory.newTransformer(xlsCate);
        
        transformer.transform(new StreamSource(is), domRs);
        
        return domRs;
    }
    
    public static DOMResult crawlUsingStringInput(String xmlString, String xslPath) throws TransformerConfigurationException, TransformerException{
        StreamSource xlsCate = new StreamSource(xslPath);
//        InputStream is = new ByteArrayInputStream(StandardCharsets.UTF_8.encode(xmlString).array());
        StringReader reader = new StringReader(xmlString);

        
        TransformerFactory factory  = TransformerFactory.newInstance();
        DOMResult domRs = new DOMResult();
        UltimateURIResolver resolver = new UltimateURIResolver();
        
        factory.setURIResolver(resolver);
        Transformer transformer = factory.newTransformer(xlsCate);
        
        transformer.transform(new StreamSource(reader), domRs);
        
        return domRs;
    }
}
