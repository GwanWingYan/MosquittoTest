package com.megrx.java.packet;

import com.megrx.java.Util;

public class ConnackPacket {

    private int returnCode;

    public ConnackPacket(byte[] rawData) {
        returnCode = Util.unsignedByteToInt(rawData[3]);
    }

    public int getReturnCode() {
        return returnCode;
    }
}
