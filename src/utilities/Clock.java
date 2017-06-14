/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import javafx.application.Platform;
import javafx.beans.property.SimpleLongProperty;

/**
 *
 * @author Bear
 */
public class Clock {
    private long begin;
    private long end;
    private long current;
    public static SimpleLongProperty visibleCurrent = new SimpleLongProperty(0);
    private long delta;
    private long duration;
    private ThreadClock clock;
    public void beginRecord(){
        current = begin = System.currentTimeMillis();
        duration = 0;
        delta = 0;
        end = 0;
        clock = new ThreadClock();
        clock.start();
    }
    public long getCurrent(){
        return current - begin;
    }
    public void endRecord(){
        clock.stopRunning();
        end = current;
        duration = end - begin;
        visibleCurrent.set(0);
    }
    public void pauseRecord(){
        clock.stopRunning();
        delta = System.currentTimeMillis() - begin;
    }
    public void proceedRecord(){
        current = System.currentTimeMillis();
        begin = current - delta;
        clock = new ThreadClock();
        clock.start();
    }
    
    public void beginPlay(){
        current = begin = System.currentTimeMillis();
        end = begin + duration;
        clock = new ThreadClock();
        clock.start();
    }
    public void endPlay(){
        clock.stopRunning();
        visibleCurrent.set(0);
    }
    public void pausePlay(){
        clock.stopRunning();
        delta = current - begin;
    }
    public void proceedPlay(){
        current = System.currentTimeMillis();
        begin = current - delta;
        end = current + duration;
        clock = new ThreadClock();
        clock.start();
    }
    private class ThreadClock extends Thread{
        boolean stop = false;
        public ThreadClock(){
            super();
        }
        @Override
        public void run(){
            while(!stop){
                current = System.currentTimeMillis();
            }
        }
        public void stopRunning(){
            stop = true;
        }
    }
}
