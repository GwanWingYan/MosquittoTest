package com.megrx.java.testcase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public abstract class TestCase {

    protected static final String HOST = "localhost";
    protected static final int PORT = 1883;     // default port of mosquitto server
    protected static final int BUFFER_SIZE = 8 * 1024;
    protected static final int DELAY_TIME = 10 * 1000;
    protected static Socket socket;
    protected static InputStream input;
    protected static OutputStream output;

    public void run() {
        // create connection
        try {
            socket = new Socket(HOST, PORT);
        } catch (IOException e) {
            System.out.println("Can't establish TCP connection");
            e.printStackTrace();
        }

        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();

            // execute
            testLogic();

            Thread.sleep(DELAY_TIME);  // to show the establishment of connection
        } catch (Exception e) {
            System.out.println("I/O Error");
            e.printStackTrace();
        } finally {
            try {
                output.close(); // this will lead to disconnection with server!
                input.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("Can't close stream or socket properly");
                e.printStackTrace();
            }
        }
    }

    protected abstract void testLogic() throws Exception;
}
