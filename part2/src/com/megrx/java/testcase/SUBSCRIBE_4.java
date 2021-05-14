package com.megrx.java.testcase;

import com.megrx.java.Util;
import com.megrx.java.packet.*;

import java.util.Arrays;

public class SUBSCRIBE_4 extends TestCase {

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
        config.topicFilters = Arrays.asList("test");
        config.requiredQOS = Arrays.asList(0);
        config.wrongReserved = true;

        // create and send SUBSCRIBE packet
        SubscribePacket subscribePacket = new SubscribePacket(config);
        byte[] subscribeData = subscribePacket.get();
        System.out.println("############### SUBSCRIBE ###############");
        System.out.println(Util.byteArrayInBinaryWithIndex(subscribeData));
        output.write(subscribeData);
        output.flush();

    }
}
