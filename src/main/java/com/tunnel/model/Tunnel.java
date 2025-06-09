package com.tunnel.model;

import com.tunnel.state.TunnelState;
import com.tunnel.state.EmptyState;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Tunnel {
    private static final Logger logger = LogManager.getLogger(Tunnel.class);
    private final ReentrantLock lock = new ReentrantLock();
    private TunnelState state;
    
    public Tunnel() {
        this.state = new EmptyState();
    }
    
    public void enter(Train train) {
        lock.lock();
        try {
            while (!state.canEnter(train)) {
                logger.info("Train {} waiting to enter tunnel", train.getId());
                Thread.yield();
            }
            state.enter(train);
        } finally {
            lock.unlock();
        }
    }
    
    public void exit(Train train) {
        lock.lock();
        try {
            state.exit(train);
        } finally {
            lock.unlock();
        }
    }
} 