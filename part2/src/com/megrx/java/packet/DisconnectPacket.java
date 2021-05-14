package com.megrx.java.packet;

import com.megrx.java.Util;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DisconnectPacket {

    private byte[] result = null;
    private boolean wrongReserverd = false;

    public DisconnectPacket() { this.wrongReserverd = false; }

    public DisconnectPacket(boolean wrongReserverd) { this.wrongReserverd = wrongReserverd; }

    public byte[] get() {
        if (result != null)
            return result;

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // write each array to output stream
        for (byte b : fixedHeader(0))
            output.write(b);

        // get byte array
        result = output.toByteArray();
        return result;
    }

    public List<Byte> fixedHeader(int remainingLength) {
        List<Byte> result = new ArrayList<>();

        byte b1 = 0;
        b1 = Util.setBit(b1, 5);
        b1 = Util.setBit(b1, 6);
        b1 = Util.setBit(b1, 7);
        if (wrongReserverd) b1 = Util.setBit(b1, 0);
        result.add(b1);

        result.add((byte)remainingLength);

        return result;
    }

    private List<Byte> variableHeader() {
        return null;
    }

    private List<Byte> payload() {
        return null;
    }
}
