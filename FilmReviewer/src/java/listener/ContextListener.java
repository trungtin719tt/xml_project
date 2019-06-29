package listener;


import java.util.Timer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import timer_task.CrawlerTimerTask;

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
        CrawlerTimerTask ctt = new CrawlerTimerTask();
        
        Timer t = new Timer();
        t.scheduleAtFixedRate(ctt, 0, 5*1000);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
