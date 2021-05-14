package com.megrx.java.packet;

import java.util.List;

public class UnsubscribePacketConfig {

    public int packetIdentifier = 10;
    public List<String> topicFilters;

    public boolean wrongReserved = false;
}
