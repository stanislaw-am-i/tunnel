package com.tunnel.state;

import com.tunnel.model.Direction;
import com.tunnel.model.Train;

public interface TunnelState {
    boolean canEnter(Train train);
    void enter(Train train);
    void exit(Train train);
    Direction getCurrentDirection();
} 