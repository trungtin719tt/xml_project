/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timer_task;

import java.util.Date;
import java.util.TimerTask;

/**
 *
 * @author ASUS
 */
public class CrawlerTimerTask extends TimerTask{

    @Override
    public void run() {
        System.out.println(new Date().toString());
    }
    
}
