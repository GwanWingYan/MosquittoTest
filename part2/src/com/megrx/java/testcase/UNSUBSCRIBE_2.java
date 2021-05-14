package com.megrx.java.testcase;

import com.megrx.java.packet.*;

import java.util.Arrays;

public class UNSUBSCRIBE_2 extends TestCase {

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
        config.wrongReserved = true;
        UnsubscribePacket unsubscribePacket = new UnsubscribePacket(config);
        byte[] unsubscribeData = unsubscribePacket.get();
        output.write(unsubscribeData);
        output.flush();
    }
}
