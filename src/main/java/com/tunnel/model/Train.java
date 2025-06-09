package com.tunnel.model;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tunnel.config.TunnelConfig;

public class Train implements Callable<Void> {
    private static final Logger logger = LogManager.getLogger(Train.class);
    private final int id;
    private final Direction direction;
    private final Tunnel tunnel;
    
    public Train(int id, Direction direction, Tunnel tunnel) {
        this.id = id;
        this.direction = direction;
        this.tunnel = tunnel;
    }
    
    @Override
    public Void call() throws Exception {
        logger.info("Train {} approaching tunnel in direction {}", id, direction);
        tunnel.enter(this);
        logger.info("Train {} entered tunnel", id);
        TimeUnit.SECONDS.sleep(TunnelConfig.getInstance().getTunnelPassageTime());
        tunnel.exit(this);
        logger.info("Train {} exited tunnel", id);
        return null;
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    public int getId() {
        return id;
    }
} 