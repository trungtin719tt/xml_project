/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import dao.BookDAO;
import dao.PageDAO;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import jaxb.book.Book;
import jaxb.booklinklist.BookLinkList;
import jaxb.source.Source;
import jaxb.totalpage.TotalPage;

/**
 *
 * @author ASUS
 */
public class Crawl implements Serializable{
    
    public static void crawl(String xmlFile, String xslFileLinkList, String xslFileBook, boolean isXmlString) {
        DOMResult rs;
        TransformerFactory factory;
        Transformer transformer;
        Source source= new Source();
//        StreamResult sr;
        StringWriter writer;
        try {
            if (isXmlString){
                source = JaxbUtils.parseXMLStringToObject(source, xmlFile);
                rs = CrawlerUltimate.crawlUsingStringInput(xmlFile, xslFileLinkList);
            }else{
                source = JaxbUtils.parseToObject(source, xmlFile);
                rs = CrawlerUltimate.crawl(xmlFile, xslFileLinkList);
            }
            
            writer = new StringWriter();
            factory = TransformerFactory.newInstance();
            transformer = factory.newTransformer();
//            sr = new StreamResult(new FileOutputStream(outputFile));
            transformer.transform(new DOMSource(rs.getNode()), new StreamResult(writer));
            String linkListXMLString = writer.getBuffer().toString().replaceAll("\n|\r", "");
            String hashCode = HashUtils.getSHA(linkListXMLString);
            PageDAO pageDAO = new PageDAO();
            if (pageDAO.IsPageCrawled(hashCode)){
                return;
            }

            BookLinkList bookLinkList = new BookLinkList();
            bookLinkList = JaxbUtils.parseXMLStringToObject(bookLinkList, linkListXMLString);
            for (String link : bookLinkList.getBookLink()) {
                String host = link.substring(0, link.indexOf("book"));
                String xmlString = CreateCrawlSourceStream(host, link);
                if (xmlString == null){
                    continue;
                }
                crawlBook(xmlString, xslFileBook);
            }
            pageDAO.addNewPage(source.getLink(), hashCode);
        } catch (FileNotFoundException ex) {
            System.out.println("not found");
            ex.printStackTrace();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally{
        }
    }
    
    public static void crawlBook(String xmlString, String xslFileBook) {
        DOMResult rs;
        TransformerFactory factory;
        Transformer transformer;
        StringWriter writer;
        try {
            rs = CrawlerUltimate.crawlUsingStringInput(xmlString, xslFileBook);
            
            writer = new StringWriter();
            factory = TransformerFactory.newInstance();
            transformer = factory.newTransformer();
            transformer.transform(new DOMSource(rs.getNode()), new StreamResult(writer));
            String bookString = writer.getBuffer().toString().replaceAll("\n|\r", "");
            String hashCode = HashUtils.getSHA(bookString);
            BookDAO bookDAO = new BookDAO();
            if (bookDAO.IsBookCrawled(hashCode)){
                return;
            }

            Book book = new Book();
            book = JaxbUtils.parseXMLStringToObject(book, bookString);
            bookDAO.AddBook(book, hashCode);
            
//            saveToDB(bookLinkList);
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally{
        }
    }
    
    public static int getTotalPage(String xmlFile, String xslTotalPage) {
        DOMResult rs;
        TransformerFactory factory;
        Transformer transformer;
        StringWriter writer;
        try {
//            rs = CrawlerUltimate.crawlUsingStringInput(xmlString, xslTotalPage);
            rs = CrawlerUltimate.crawl(xmlFile, xslTotalPage);
            
            writer = new StringWriter();
            factory = TransformerFactory.newInstance();
            transformer = factory.newTransformer();
            transformer.transform(new DOMSource(rs.getNode()), new StreamResult(writer));
            String xmlString = writer.getBuffer().toString().replaceAll("\n|\r", "");

            TotalPage totalPage = new TotalPage();
            totalPage = JaxbUtils.parseXMLStringToObject(totalPage, xmlString);
            return totalPage.getNumOfPage();
            
//            saveToDB(bookLinkList);
        } catch (TransformerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally{
        }
        return -1;
    }
    
    public static String CreateCrawlSourceStream(String host, String link){
        Source source = new Source();
        source.setHost(host);
        source.setLink(link);
        try {
            return JaxbUtils.parseToXMLString(source);
        } catch (JAXBException ex) {
            Logger.getLogger(Crawl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
}
