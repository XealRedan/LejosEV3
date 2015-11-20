package fr.utbm.lejosev3.tr54.tp3.ping;

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

/**
 * Emit ping packets
 */
public class PingEmitter {
    /** The time-out in ms */
    private static final int TIMEOUT_MS = 500;

    /** The packet buffer sent for ping */
    private static final byte[] PACKET = new byte[1];

    /** The reception buffer */
    private static final byte[] RECEPTION_BUFFER = new byte[256];

    /** The packet used to get the answer */
    private static final DatagramPacket RECEPTION_PACKET = new DatagramPacket(RECEPTION_BUFFER, RECEPTION_BUFFER.length);

    /** The broadcast address used to send ping */
    private InetAddress broadcastAddress;
    /** The port used to send ping */
    private int port;

    /** The datagram socket used to send packets */
    private DatagramSocket socket;

    /** The packet that will be send for the ping */
    private DatagramPacket packet;

    /**
     * Build a ping emitter with a given broadcast address and port
     * @param broadcastAddress the broadcast address
     * @param port the port
     * @throws SocketException thrown if unable to create socket
     */
    public PingEmitter(InetAddress broadcastAddress, int port) throws SocketException {
        this.broadcastAddress = broadcastAddress;
        this.port = port;

        // The socket is created with a random available port
        this.socket = new DatagramSocket();

        // If the receiver does not respond within time-out, we will return Long.MAX_VALUE as ping value
        this.socket.setSoTimeout(TIMEOUT_MS);

        this.packet = new DatagramPacket(PACKET, PACKET.length, this.broadcastAddress, this.port);
    }

    /**
     * Gets the ping value
     * @return the ping value in ms, or Integer.MAX_VALUE if an exception occurred (i.e. time-out)
     */
    public long ping() {
        try {
            long tic = System.currentTimeMillis();

            // No operations are allowed between send and receive to avoid risks of answers received before they are listened to
            this.socket.send(this.packet);
            this.socket.receive(RECEPTION_PACKET);

            // Compute elapsed time and return it
            return System.currentTimeMillis() - tic;
        } catch (IOException e) {
            //
        }

        return Long.MAX_VALUE;
    }
}
