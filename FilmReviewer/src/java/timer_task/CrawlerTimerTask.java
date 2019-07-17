/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timer_task;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.xml.bind.JAXBException;
import jaxb.source.Source;
import utilities.Crawl;
import utilities.JaxbUtils;

/**
 *
 * @author ASUS
 */
public class CrawlerTimerTask extends TimerTask{

    private ServletContextEvent sce;
    public CrawlerTimerTask(ServletContextEvent sce) {
        this.sce = sce;
    }

    
    @Override
    public void run() {
        System.out.println("timer");
        String realPath = sce.getServletContext().getRealPath("/");
        System.out.println(realPath);
        Crawl.crawl(realPath + "/WEB-INF/xml/goodreads.xml", realPath + "/WEB-INF/xsl/goodreads.xsl", realPath + "/WEB-INF/xsl/goodreads_book.xsl", false);
        int totalPage = Crawl.getTotalPage(realPath + "/WEB-INF/xml/goodreads.xml", realPath + "/WEB-INF/xsl/goodreads_totalpage.xsl");
        System.out.println("NumOfPage: " + totalPage);
        Source source = new Source();
        try {
            source = JaxbUtils.parseToObject(source ,realPath + "/WEB-INF/xml/goodreads.xml");
            
        } catch (JAXBException ex) {
            Logger.getLogger(CrawlerTimerTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        String link = source.getLink();
        for (int i = 255; i <= totalPage; i++) {
            source.setLink(link+"?page=" + i);
            String xmlString = null;
            try {
                xmlString = JaxbUtils.parseToXMLString(source);
            } catch (JAXBException ex) {
                Logger.getLogger(CrawlerTimerTask.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (xmlString == null){
                continue;
            }
            Crawl.crawl(xmlString, realPath + "/WEB-INF/xsl/goodreads.xsl", realPath + "/WEB-INF/xsl/goodreads_book.xsl", true);
        }
    }
    
}
