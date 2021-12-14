//===----------------------------------------------------------------------===//
//
//                         JA
//
// server.java
//
// Identification: serverc/server.java
//
// Usage: Server Process, listen to the port, while there is a 
// Socket request, create a thread serverthr, and start
//
// Last Modified : 2021.12.15 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//

package serverc;

import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String st[]) {
        try {
            // Setup ServerSocket object with port number 12345
            // Implement infinite loop for handling muliple clients request
            // Accept client request with accept method
            // Create and start ClientThread
            ServerSocket ss = new ServerSocket(12345); // ss only can be closed handly (Ctrl-c)
            while (true) {
                Socket socket = ss.accept(); // listen in socket 12345
		// multiple serverthr shares a same threads pool
		// when there is a 
                serverthr th=new serverthr(socket); // while receve a requst, create a new thread
                th.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

