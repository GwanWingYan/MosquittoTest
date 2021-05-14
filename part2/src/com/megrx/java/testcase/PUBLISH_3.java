package com.megrx.java.testcase;

import com.megrx.java.Util;
import com.megrx.java.packet.ConnectPacket;
import com.megrx.java.packet.PublishPacket;
import com.megrx.java.packet.PublishPacketConfig;

public class PUBLISH_3 extends TestCase {

    protected void testLogic() throws Exception {

        // connect to broker
        byte[] connectData = ConnectPacket.getDefaultConnectPacket().get();
        output.write(connectData);
        output.flush();

        // configuration
        PublishPacketConfig config = new PublishPacketConfig();
        config.dupFlag = false;
        config.QOSLevel = 0;
        config.retainFlag = true;
        config.topicName = "test";
        config.packetIdentifier = 10;
        config.message = "1st retain";

        // send 1st PUBLISH packet
        PublishPacket packet = new PublishPacket(config);
        byte[] publishData = packet.get();
        System.out.println("############### PUBLISH ###############");
        System.out.println(Util.byteArrayInBinaryWithIndex(publishData));
        output.write(publishData);
        output.flush();

        Thread.sleep(1000);

        // send 2nd PUBLISH packet
        config.retainFlag = true;
        config.message = "2nd retain";
        packet = new PublishPacket(config);
        publishData = packet.get();
        System.out.println("############### PUBLISH ###############");
        System.out.println(Util.byteArrayInBinaryWithIndex(publishData));
        output.write(publishData);
        output.flush();
    }
}
