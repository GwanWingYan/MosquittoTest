package com.megrx.java.packet;

import com.megrx.java.Util;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubscribePacket {

    private SubscribePacketConfig config;
    private byte[] result = null;

    public SubscribePacket(SubscribePacketConfig config) {
        this.config = config;
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
        b1 = Util.setBit(b1, 1);
        b1 = Util.setBit(b1, 7);
        if (config.wrongReserved) b1 = Util.setBit(b1, 0);
        result.add(b1);

        List<Byte> lengthByteList = Util.encodeLength(remainingLength);
        result.addAll(lengthByteList);

        return result;
    }

    private List<Byte> variableHeader() {
        List<Byte> result = new ArrayList<>();
        int msb;
        int lsb;

        // Packet Identifier
        msb = config.packetIdentifier / 256;
        lsb = config.packetIdentifier % 256;
        result.add((byte)msb);
        result.add((byte)lsb);

        return result;
    }

    private List<Byte> payload() {

        List<Byte> result = new ArrayList<>();
        if (config.topicFilters.size() > 0 &&
                config.requiredQOS.size() == config.topicFilters.size()) {
            for (int i = 0; i < config.topicFilters.size(); ++i) {
                Util.encodeUTF8String(result, config.topicFilters.get(i));
                result.add((byte)(int)config.requiredQOS.get(i));
            }
        }

        return result;
    }

    public static SubscribePacket getDefaultConnectPacket(boolean dualTopic) {

        SubscribePacketConfig config = new SubscribePacketConfig();
        config.packetIdentifier = 10;
        if (dualTopic) {
            config.topicFilters = Arrays.asList("test", "exam");
            config.requiredQOS = Arrays.asList(0, 0);
        } else {
            config.topicFilters = Arrays.asList("test");
            config.requiredQOS = Arrays.asList(0);
        }

        return new SubscribePacket(config);
    }
}
