package com.tunnel.state;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import com.tunnel.config.TunnelConfig;
import com.tunnel.model.Direction;
import com.tunnel.model.Train;

public class EmptyState implements TunnelState {
    private final ReentrantLock lock = new ReentrantLock();
    private final AtomicInteger trainsInTunnel = new AtomicInteger(0);
    private final AtomicInteger consecutiveTrains = new AtomicInteger(0);
    private Direction currentDirection;
    
    @Override
    public boolean canEnter(Train train) {
        lock.lock();
        try {
            if (trainsInTunnel.get() == 0) {
                return true;
            }
            if (train.getDirection() == currentDirection) {
                return consecutiveTrains.get() < TunnelConfig.getInstance().getMaxConsecutiveTrains();
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public void enter(Train train) {
        lock.lock();
        try {
            if (trainsInTunnel.get() == 0) {
                currentDirection = train.getDirection();
                consecutiveTrains.set(1);
            } else {
                consecutiveTrains.incrementAndGet();
            }
            trainsInTunnel.incrementAndGet();
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public void exit(Train train) {
        lock.lock();
        try {
            trainsInTunnel.decrementAndGet();
            if (trainsInTunnel.get() == 0) {
                consecutiveTrains.set(0);
                currentDirection = null;
            }
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public Direction getCurrentDirection() {
        return currentDirection;
    }
} 