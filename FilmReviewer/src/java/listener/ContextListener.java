package listener;


import java.util.Timer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import timer_task.CrawlerTimerTask;
import utilities.Crawl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class ContextListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Timer timer = new Timer();
        CrawlerTimerTask timerTask = new CrawlerTimerTask(sce);
        timer.scheduleAtFixedRate(timerTask, 0, 1000*60*60*24);
//        Crawl.crawlData(realPath + "/xml/metacritic.xml", realPath + "/xsl/metacritic.xsl", realPath + "/xml/metacritic_output.xml");
//        Crawl.crawlData(realPath + "/xsl/nhaxinh.xml", realPath + "/xsl/nhaxinh.xsl", realPath + "/xml/nhaxinh-out.xml");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
