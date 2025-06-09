package com.tunnel.config;

import java.util.concurrent.atomic.AtomicReference;

public class TunnelConfig {
    private static final AtomicReference<TunnelConfig> instance = new AtomicReference<>();
    
    private final int maxTrainsInTunnel;
    private final int maxConsecutiveTrains;
    private final int tunnelPassageTime;
    
    private TunnelConfig(int maxTrainsInTunnel, int maxConsecutiveTrains, int tunnelPassageTime) {
        this.maxTrainsInTunnel = maxTrainsInTunnel;
        this.maxConsecutiveTrains = maxConsecutiveTrains;
        this.tunnelPassageTime = tunnelPassageTime;
    }
    
    public static TunnelConfig getInstance() {
        return instance.updateAndGet(config -> {
            if (config == null) {
                throw new IllegalStateException("Configuration not initialized");
            }
            return config;
        });
    }
    
    public static void initialize(int maxTrainsInTunnel, int maxConsecutiveTrains, int tunnelPassageTime) {
        instance.compareAndSet(null, new TunnelConfig(maxTrainsInTunnel, maxConsecutiveTrains, tunnelPassageTime));
    }
    
    // Getters
    public int getMaxTrainsInTunnel() { return maxTrainsInTunnel; }
    public int getMaxConsecutiveTrains() { return maxConsecutiveTrains; }
    public int getTunnelPassageTime() { return tunnelPassageTime; }
} 