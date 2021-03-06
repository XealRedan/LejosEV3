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


import lejos.hardware.Button;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by alombard on 20/11/2015.
 */
public class RobotPingEmitter {
    public static void main(String[] args) throws UnknownHostException, SocketException, InterruptedException {
        final PingEmitter pingEmitter = new PingEmitter(InetAddress.getLocalHost(), 5555);

        // Wait for any press and set a variable to true when the key is pressed
        final boolean[] stop = new boolean[1];

        new Thread(new Runnable() {
            public void run() {
                Button.waitForAnyPress();
                stop[0] = true;
            }
        }).start();

        // Send a ping and print its value every 500ms until a button is pressed
        while(!stop[0]) {
            long ping = pingEmitter.ping();

            System.out.println(ping);

            Thread.sleep(500);
        }
    }
}
