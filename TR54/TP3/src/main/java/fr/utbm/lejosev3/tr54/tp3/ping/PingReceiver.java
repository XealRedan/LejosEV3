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
import java.net.SocketException;

/**
 * Receive and answer to ping packets
 */
public class PingReceiver {
    /** The reception buffer */
    private static final byte[] RECEPTION_BUFFER = new byte[256];

    /** The packet used to receive the ping */
    private static final DatagramPacket RECEPTION_PACKET = new DatagramPacket(RECEPTION_BUFFER, RECEPTION_BUFFER.length);

    /** The buffer of the answer */
    private static final byte[] ANSWER_BUFFER = new byte[1];

    /** The port used to receive ping */
    private int port;

    /** The socket used to receive the ping */
    private DatagramSocket socket;

    /**
     * Build a ping receiver with the given port
     * @param port the reception port
     * @throws SocketException thrown if the creation of the datagram socket failed
     */
    public PingReceiver(int port) throws SocketException {
        this.port = port;
        this.socket = new DatagramSocket(port);
    }

    /**
     * Answer a ping (blocking call)
     * @throws IOException thrown if either the reception or the sending operation failed
     */
    public void answerPing() throws IOException {
        // Operations between receive and send are limited to avoid latency due to computation
        this.socket.receive(RECEPTION_PACKET);
        // A packet is re-emitted toward the address/port of the sending socket
        this.socket.send(new DatagramPacket(ANSWER_BUFFER, ANSWER_BUFFER.length, RECEPTION_PACKET.getAddress(), RECEPTION_PACKET.getPort()));
    }
}
