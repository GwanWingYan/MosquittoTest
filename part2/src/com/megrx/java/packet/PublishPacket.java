package com.megrx.java.packet;

import com.megrx.java.Util;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PublishPacket {

    private PublishPacketConfig config;
    private byte[] result = null;

    public PublishPacket(PublishPacketConfig config) {
        this.config = config;
    }

    public static String getMessage(byte[] rawData) {
        int remainingLength = 0;    // length of variable header and payload
        int index = 1;
        byte b = 0;

        b = rawData[index++];
        while (Util.unsignedByteToInt(b) / 128 == 1 && index <= 4) {
            remainingLength = 128 * remainingLength + Util.unsignedByteToInt(b);
            b = rawData[index++];
        }

        int variableLength = 0;     // length of variable header
        variableLength =
                Util.unsignedByteToInt(rawData[index]) * 256
                + Util.unsignedByteToInt(rawData[index + 1]);

        byte[] messageBytes = Arrays.copyOfRange(rawData, index + variableLength + 2, rawData.length);
        String message = new String(messageBytes, StandardCharsets.UTF_8);

        return message;
    }

    public byte[] get() {
        if (result != null)
            return result;

        int remainingLength;
        List<Byte> fixedHeader;
        List<Byte> variableHeader;
        List<Byte> payload;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // variable header
        variableHeader = variableHeader();

        // payload
        payload = payload();

        // length of variable header and payload
        remainingLength = variableHeader.size() + payload.size();

        // fixed header
        fixedHeader = fixedHeader(remainingLength);

        // write each array to output stream
        for (byte b : fixedHeader)      output.write(b);
        for (byte b : variableHeader)   output.write(b);
        for (byte b : payload)          output.write(b);

        // get byte array
        result = output.toByteArray();
        return result;
    }

    public List<Byte> fixedHeader(int remainingLength) {
        List<Byte> result = new ArrayList<>();

        byte b1 = 0;
        if (config.retainFlag)          b1 = Util.setBit(b1, 0);
        if (config.QOSLevel % 2 == 1)   b1 = Util.setBit(b1, 1);
        if (config.QOSLevel / 2 == 1)   b1 = Util.setBit(b1, 2);
        if (config.dupFlag)             b1 = Util.setBit(b1, 3);
        b1 = Util.setBit(b1, 4);
        b1 = Util.setBit(b1, 5);
        result.add(b1);

        List<Byte> lengthByteList = Util.encodeLength(remainingLength);
        result.addAll(lengthByteList);

        return result;
    }

    private List<Byte> variableHeader() {
        List<Byte> result = new ArrayList<>();
        int msb;
        int lsb;

        // Topic name
        Util.encodeUTF8String(result, config.topicName);

        // Packet Identifier
        msb = config.packetIdentifier / 256;
        lsb = config.packetIdentifier % 256;
        result.add((byte)msb);
        result.add((byte)lsb);

        return result;
    }

    private List<Byte> payload() {

        List<Byte> result = new ArrayList<>();
        if (config.message.length() > 0) {
            for (byte b : config.message.getBytes(StandardCharsets.UTF_8)) {
                result.add(b);
            }
        }

        return result;
    }
}
