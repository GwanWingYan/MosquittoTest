package com.megrx.java.packet;

import java.util.Arrays;
import java.util.List;

public class SubscribePacketConfig {

    public int packetIdentifier = 10;
    public List<String> topicFilters;
    public List<Integer> requiredQOS;

    public boolean wrongReserved = false;
}
