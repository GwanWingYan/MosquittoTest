package com.megrx.java.packet;

public class PublishPacketConfig {

    public boolean dupFlag = false;
    public int QOSLevel = 0;
    public boolean retainFlag = false;
    public String topicName = "test";
    public int packetIdentifier = 10;
    public String message = "Hey there!";
}
