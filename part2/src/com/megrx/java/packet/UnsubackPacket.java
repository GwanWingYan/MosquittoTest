package com.megrx.java.packet;

import com.megrx.java.Util;

public class UnsubackPacket {

    private int packetIdentifier;

    public UnsubackPacket(byte[] rawData) {
        packetIdentifier =
                Util.unsignedByteToInt(rawData[2]) * 256
                + Util.unsignedByteToInt(rawData[3]);
    }

    public int getPacketIdentifier() { return packetIdentifier; }
}
