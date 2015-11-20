package fr.utbm.lejosev3.tr54.tp3.synchronization;

/*
 * #%L
 * TP3
 * %%
 * Copyright (C) 2015 Alexandre Lombard
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

/**
 * Broadcast its internal time
 * Answers are not expected
 */
public class TimeEmitter {
    /** The broadcast address used to send the packet */
    private InetAddress broadcastAddress;
    /** The port used to send the packet */
    private int port;

    /** The datagram socket used to send packets */
    private DatagramSocket socket;

    /** The id of the current packet (incremented each time a packet is sent) */
    private long packetId = 0;

    /**
     * Build a time emitter
     * @param broadcastAddress the broadcast address
     * @param port the port used
     * @throws SocketException thrown if the socket cannot be built
     */
    public TimeEmitter(InetAddress broadcastAddress, int port) throws SocketException {
        this.broadcastAddress = broadcastAddress;
        this.port = port;

        this.socket = new DatagramSocket();
    }

    /**
     * Broadcast a packet containing (packetId, time)
     * @throws IOException thrown if the packet was not sent
     */
    public void broadcastTime() throws IOException {
        // Get the current time
        long time = System.currentTimeMillis();

        // Allocate a byte buffer with 16 bytes to store the packet id and the time
        final ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.putLong(this.packetId);
        byteBuffer.putLong(time);

        // Build and send the packet
        this.socket.send(new DatagramPacket(byteBuffer.array(), byteBuffer.array().length, this.broadcastAddress, this.port));

        // Update the packet ID
        this.packetId++;
    }
}
