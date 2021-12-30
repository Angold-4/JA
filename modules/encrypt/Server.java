//===----------------------------------------------------------------------===//
//
//                         JA
//
// server.java
//
// Identification: encrypt/Server.java
//
// Usage: Server Process, listen to the port, while there is a 
// Socket request, create a thread serverthr, and start
//
// Last Modified : 2021.12.31 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//

package encrypt;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String st[]) {
	try {
	    ServerSocket ss = new ServerSocket(12345);
	    while (true) {
		Socket socket = ss.accept();
		ServerThread th = new ServerThread(socket);
		th.start();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}


