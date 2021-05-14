package com.megrx.java.packet;

import com.megrx.java.Util;

import java.util.ArrayList;
import java.util.List;

public class SubackPacket {

    private List<Integer> returnCodes;
    private int packetIdentifier;

    public SubackPacket(byte[] rawData) {
        int remainingLength = 0;    // length of variable header and payload
        int index = 1;
        byte b = 0;
        returnCodes = new ArrayList<>();

        b = rawData[index++];
        while (Util.unsignedByteToInt(b) / 128 == 1 && index <= 4) {
            b = rawData[index++];
        }

        packetIdentifier =
                Util.unsignedByteToInt(rawData[index]) * 256
                + Util.unsignedByteToInt(rawData[index + 1]);

        index += 2;
        for (; index < rawData.length; ++index) {
            returnCodes.add(Util.unsignedByteToInt(rawData[index]));
        }
    }

    public List<Integer> getReturnCodes() {
        return returnCodes;
    }

    public int getPacketIdentifier() { return packetIdentifier; }
}
