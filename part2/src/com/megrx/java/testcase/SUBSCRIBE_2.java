package com.megrx.java.testcase;

import com.megrx.java.Util;
import com.megrx.java.packet.*;

import java.util.Arrays;

public class SUBSCRIBE_2 extends TestCase {

    @Override
    protected void testLogic() throws Exception {

        // connect to broker
        byte[] connectData = ConnectPacket.getDefaultConnectPacket().get();
        output.write(connectData);
        output.flush();
        byte[] connackData = new byte[BUFFER_SIZE];
        int byteRead = input.read(connackData);

        // configuration
        SubscribePacketConfig config = new SubscribePacketConfig();
        config.packetIdentifier = 10;
        config.topicFilters = Arrays.asList("test", "exam");
        config.requiredQOS = Arrays.asList(0, 0);

        // create and send SUBSCRIBE packet
        SubscribePacket subscribePacket = new SubscribePacket(config);
        byte[] subscribeData = subscribePacket.get();
        System.out.println("############### SUBSCRIBE ###############");
        System.out.println(Util.byteArrayInBinaryWithIndex(subscribeData));
        output.write(subscribeData);
        output.flush();

        // receive suback packet
        byte[] subackData = new byte[BUFFER_SIZE];
        byteRead = input.read(subackData);
        subackData = Arrays.copyOfRange(subackData, 0, byteRead);
        SubackPacket subackPacket = new SubackPacket(subackData);
        System.out.println("############### SUBACK ###############");
        System.out.println("Packet id: " + subackPacket.getPacketIdentifier());
        for (int i = 0 ; i < subackPacket.getReturnCodes().size(); ++i) {
            System.out.println("Return code " + (i + 1) + ": " + subackPacket.getReturnCodes().get(i));
        }

        byte[] publishData = new byte[BUFFER_SIZE];
        byteRead = input.read(publishData);
        publishData = Arrays.copyOfRange(publishData, 0, byteRead);
        String message = PublishPacket.getMessage(publishData);
        System.out.println("############### PUBLISH ###############");
        System.out.println(message);
    }
}
