package com.megrx.java.testcase;

import com.megrx.java.packet.*;

import java.util.Arrays;

public class UNSUBSCRIBE_1 extends TestCase {

    @Override
    protected void testLogic() throws Exception {
        // connect to broker
        byte[] connectData = ConnectPacket.getDefaultConnectPacket().get();
        output.write(connectData);
        output.flush();
        byte[] connackData = new byte[BUFFER_SIZE];
        int byteRead = input.read(connackData);

        // send SUBSCRIBE packet
        SubscribePacket subscribePacket = SubscribePacket.getDefaultConnectPacket(true);
        byte[] subscribeData = subscribePacket.get();
        output.write(subscribeData);
        output.flush();

        // receive SUBACK packet
        byte[] subackData = new byte[BUFFER_SIZE];
        byteRead = input.read(subackData);

        // send UNSUBSCRIBE packet
        UnsubscribePacketConfig config = new UnsubscribePacketConfig();
        config.packetIdentifier = 10;
        config.topicFilters = Arrays.asList("test");
        UnsubscribePacket unsubscribePacket = new UnsubscribePacket(config);
        byte[] unsubscribeData = unsubscribePacket.get();
        output.write(unsubscribeData);
        output.flush();

        // receive UNSUBACK packet
        byte[] unsubackData = new byte[BUFFER_SIZE];
        byteRead = input.read(unsubackData);
        unsubackData = Arrays.copyOfRange(unsubackData, 0, byteRead);
        UnsubackPacket unsubackPacket = new UnsubackPacket(unsubackData);
        System.out.println("############### UNSUBACK ###############");
        System.out.println("Packet id: " + unsubackPacket.getPacketIdentifier());

        // receive PUBLISH packet
        byte[] publishData = new byte[BUFFER_SIZE];
        byteRead = input.read(publishData);
        publishData = Arrays.copyOfRange(publishData, 0, byteRead);
        String message = PublishPacket.getMessage(publishData);
        System.out.println("############### PUBLISH ###############");
        System.out.println(message);
    }
}
