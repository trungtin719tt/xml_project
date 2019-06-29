/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author ASUS
 */
public class UltimateURIResolver implements URIResolver{

    @Override
    public Source resolve(String href, String base) throws TransformerException {
        if (href != null && href.indexOf("https://vi.wikipedia.org/") == 0){
            try {
                InputStream httpResult = new URL(href).openStream();
                URL url = new URL(href);
                System.out.println(href);
                URLConnection connection = url.openConnection();
                connection.setReadTimeout(30 * 1000);
                connection.setConnectTimeout(30 * 1000);
                StreamSource ss = preProcessInputStream(connection.getInputStream());
                return ss;
            } catch (MalformedURLException ex) {
                Logger.getLogger(UltimateURIResolver.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(UltimateURIResolver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    private StreamSource preProcessInputStream(InputStream httpResult) throws IOException{
        String textContent = TextUtils.getString(httpResult);
        textContent = TextUtils.refineHTML(textContent);
        InputStream htmlResult = new ByteArrayInputStream(textContent.getBytes("UTF-8"));
        return new StreamSource(htmlResult);
    }
    
}
