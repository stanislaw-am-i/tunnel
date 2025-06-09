package com.tunnel.util;

import java.io.InputStream;
import java.util.Properties;

import com.tunnel.config.TunnelConfig;

public class ConfigLoader {
    public static void loadConfig(String configPath) {
        try (InputStream configStream = ConfigLoader.class.getClassLoader().getResourceAsStream(configPath)) {
            Properties props = new Properties();
            props.load(configStream);
            
            int maxTrainsInTunnel = Integer.parseInt(props.getProperty("maxTrainsInTunnel").trim());
            int maxConsecutiveTrains = Integer.parseInt(props.getProperty("maxConsecutiveTrains").trim());
            int tunnelPassageTime = Integer.parseInt(props.getProperty("tunnelPassageTime").trim());
            
            TunnelConfig.initialize(maxTrainsInTunnel, maxConsecutiveTrains, tunnelPassageTime);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }
} 