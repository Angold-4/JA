//===----------------------------------------------------------------------===//
//
//                         JA
//
// producer.java
//
// Identification: buffercon/producer.java
//
// Usage: a simple object just put the number into buffer (every 0.5s)
//
// Last Modified : 2021.12.15 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//

package buffercon;

public class producer extends Thread {
    private buffer buffer;
    private int no;

    public producer(buffer c, int no) {
        buffer=c;
        this.no = no;
    }

    public void run() {
	try {
	    Thread.sleep(500);
	    buffer.put(no);
	} catch (InterruptedException ex) {
	    ex.printStackTrace();
	}
    }
}

