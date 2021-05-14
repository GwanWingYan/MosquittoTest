package com.megrx.java.testcase;

import com.megrx.java.packet.ConnectPacket;
import com.megrx.java.packet.DisconnectPacket;
import com.megrx.java.packet.SubscribePacket;
import com.megrx.java.packet.SubscribePacketConfig;

import java.util.Arrays;

public class DISCONNECT_1 extends TestCase {

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
        config.topicFilters = Arrays.asList("test");
        config.requiredQOS = Arrays.asList(0);

        // send SUBSCRIBE packet
        SubscribePacket subscribePacket = new SubscribePacket(config);
        byte[] subscribeData = subscribePacket.get();
        output.write(subscribeData);
        output.flush();

        // receive SUBACK packet
        byte[] subackData = new byte[BUFFER_SIZE];
        byteRead = input.read(subackData);

        // send DISCONNECT packet
        DisconnectPacket disconnectPacket = new DisconnectPacket();
        byte[] disconnectData = disconnectPacket.get();
        output.write(disconnectData);
        output.flush();
    }
}
