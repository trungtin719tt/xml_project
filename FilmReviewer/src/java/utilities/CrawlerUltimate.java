/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
        
        TransformerFactory factory  = TransformerFactory.newInstance();
        DOMResult domRs = new DOMResult();
        UltimateURIResolver resolver = new UltimateURIResolver();
        
        factory.setURIResolver(resolver);
        Transformer transformer = factory.newTransformer(xlsCate);
        
        transformer.transform(new StreamSource(is), domRs);
        return domRs;
    }
}
