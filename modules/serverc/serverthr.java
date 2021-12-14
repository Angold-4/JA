//===----------------------------------------------------------------------===//
//
//                         JA
//
// serverthr.java
//
// Identification: serverc/serverthr.java
//
// Usage: always check if there comes a message from socket.InputStream
// if there comes a message, go through all threads (a static shared threads pool)
// and sent message to each socket.OutputStream
//
// Last Modified : 2021.12.15 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//

package serverc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class serverthr extends Thread {
    static private ArrayList<serverthr> threads = new ArrayList<serverthr>(); // static
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    public serverthr(Socket socket) {
        try {
            // Initialize socket with input parameter
            // Initialize DataOutputStream and DataInputStream
            // Add itself to threads
            this.socket = socket;
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
            threads.add(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            // User infinite loop for receiving and boardcasting message
            // Readincoming message from client
            // Implement a for loop to access all the ClientThread from threads
            // Sent message to each client
            while (true) {
                String msg=input.readUTF();
                synchronized (threads) { // just a lock
                    for (serverthr th : threads) {
                        try {
                            th.output.writeUTF(msg);
                            th.output.flush();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        } catch (Exception e) {
            threads.remove(this);
        }
    }
}
 
