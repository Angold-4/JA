//===----------------------------------------------------------------------===//
//
//                         JA
//
// ServerThread.java
//
// Identification: encrypt/ServerThread.java
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

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class ServerThread extends Thread {
    static private Vector<ServerThread> threads = new Vector<ServerThread>(); // static
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ServerThread(Socket socket) {
	try {
	    this.socket = socket;
	    output = new ObjectOutputStream(socket.getOutputStream());
	    input = new ObjectInputStream(socket.getInputStream());
	    threads.add(this);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void run() {
	try {
	    while (true) {
		byte[] data = (byte[]) input.readObject();
		synchronized (threads) {
		    for (ServerThread th : threads) {
			try {
			    th.output.writeObject(data);
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
