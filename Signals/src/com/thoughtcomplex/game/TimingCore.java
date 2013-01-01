package com.thoughtcomplex.game;

import com.thoughtcomplex.event.EventListener;
import com.thoughtcomplex.event.EventMulticaster;
import com.thoughtcomplex.event.Observable;

public class TimingCore implements Observable<TickEvent>, Runnable {
    private EventMulticaster<TickEvent> onTicked = new EventMulticaster<TickEvent>();
    private long lastTick = -1L;
    private long nanosPerTick = 12L;
    private long delta = 0L;
    private boolean keepRunning = false;
    private Thread eventThread = null;
    private boolean pause = false;
    
    public TimingCore(long nanosPerTick) {
        this.nanosPerTick = nanosPerTick;
        this.lastTick = System.nanoTime()-nanosPerTick;
    }

    @Override
    public void addListener(EventListener<TickEvent> listener) {
        onTicked.addListener(listener);
    }

    @Override
    public void removeListener(EventListener<TickEvent> listener) {
        onTicked.removeListener(listener);
    }

    public void start() {
        keepRunning = true;
        eventThread = new Thread(this);
        eventThread.start();
    }
    
    @Override
    public void run() {
        while(keepRunning) {
            long now = System.nanoTime();
            delta += now - lastTick;
            lastTick = now;
            
            //recover from nasty bog-downs
            if (delta>nanosPerTick*8) delta = nanosPerTick;
            while(delta > nanosPerTick) {
                delta-=nanosPerTick;
                if (!pause) onTicked.invoke(new TickEvent(this));
            }
        }
    }
    
    public void end() {
        keepRunning=false;
        if (eventThread!=null) try { eventThread.join(100); } catch (InterruptedException ex) {
            eventThread.interrupt();
        }
    }
    
    public void pause() {
        pause = true;
    }
    
    public boolean isActive() {
        return eventThread.isAlive();
    }
}
