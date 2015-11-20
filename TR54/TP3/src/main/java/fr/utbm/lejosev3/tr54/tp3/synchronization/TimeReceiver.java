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
 * Class used to read time from an emitter
 */
public class TimeReceiver {
    /** The reception buffer */
    private static final byte[] RECEPTION_BUFFER = new byte[16];

    /** The packet used to receive the time */
    private static final DatagramPacket RECEPTION_PACKET = new DatagramPacket(RECEPTION_BUFFER, RECEPTION_BUFFER.length);

    /** The port used to receive time */
    private int port;

    /** The socket used to receive the time */
    private DatagramSocket socket;

    /**
     * Build a time receiver with a given port
     * @param port the port used to listen to time emissions
     * @throws SocketException thrown if the socket was not created
     */
    public TimeReceiver(int port) throws SocketException {
        this.port = port;
        this.socket = new DatagramSocket(this.port);
    }

    /**
     * Reads the time from a time emitter
     * @return the time of the
     * @throws IOException thrown if receive operation failed
     */
    public long getTime() throws IOException {
        return getTime(null, null);
    }

    /**
     * Reads the time from a time emitter and fills the parameters with the source and the packetId if not null
     * @param outSource
     * @param outPacketId
     * @return the time of the emitter
     * @throws IOException thrown if receive operation failed
     */
    public long getTime(InetAddress[] outSource, long[] outPacketId) throws IOException {
        this.socket.receive(RECEPTION_PACKET);

        final ByteBuffer byteBuffer = ByteBuffer.wrap(RECEPTION_BUFFER);
        final long packetId = byteBuffer.getLong();
        final long time = byteBuffer.getLong();

        if(outSource != null && outSource.length >= 1)
            outSource[0] = RECEPTION_PACKET.getAddress();

        if(outPacketId != null && outPacketId.length >= 1)
            outPacketId[0] = packetId;

        return time;
    }
}
