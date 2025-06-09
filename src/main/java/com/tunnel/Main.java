package com.tunnel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tunnel.model.Direction;
import com.tunnel.model.Train;
import com.tunnel.model.Tunnel;
import com.tunnel.util.ConfigLoader;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    
    public static void main(String[] args) {
        try {
            ConfigLoader.loadConfig("config.properties");
            
            Tunnel tunnel = new Tunnel();
            ExecutorService executor = Executors.newFixedThreadPool(10);
            
            for (int i = 0; i < 20; i++) {
                Direction direction = i % 2 == 0 ? Direction.FORWARD : Direction.BACKWARD;
                executor.submit(new Train(i, direction, tunnel));
            }
            
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
            
        } catch (Exception e) {
            logger.error("Error in main thread", e);
        }
    }
} 