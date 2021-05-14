package com.megrx.java.testcase;

import com.megrx.java.Util;
import com.megrx.java.packet.ConnackPacket;
import com.megrx.java.packet.ConnectPacket;
import com.megrx.java.packet.ConnectPacketConfig;

import java.io.IOException;
import java.util.Arrays;

public class CONNECT_1 extends TestCase {

    protected void testLogic() throws Exception {

        // configuration
        ConnectPacketConfig config = new ConnectPacketConfig();
        config.usernameFlag = false;
        config.passwordFlag = false;
        config.willRetain = false;
        config.willQOS = 1;
        config.willFlag = true;
        config.cleanSession = true;
        config.keepAlive = 100;
        config.clientID = "gwan";
        config.willTopic = "test";
        config.willMessage = "will msg";
        config.username = "un";
        config.password = "pw";

        // create and send CONNECT packet
        ConnectPacket packet = new ConnectPacket(config);
        byte[] connectData = packet.get();
        System.out.println("############### CONNECT ###############");
        System.out.println(Util.byteArrayInBinaryWithIndex(connectData));
        output.write(connectData);
        output.flush();

        // receive CONNACK packet
        byte[] connackData = new byte[BUFFER_SIZE];
        int byteRead = input.read(connackData);
        connackData = Arrays.copyOfRange(connackData, 0, byteRead);
        System.out.println("############### CONNACK ###############");
        System.out.println("Return code: " + (new ConnackPacket(connackData)).getReturnCode());
        System.out.println(Util.byteArrayInBinaryWithIndex(connackData));
    }
}
