//===----------------------------------------------------------------------===//
//
//                         JA
//
// consumer.java
//
// Identification: buffercon/consumer.java
//
// Usage: deal with the data: consumer object
//
// Last Modified : 2021.12.15 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//


package buffercon;

import javax.swing.JTextField;

public class consumer extends JTextField implements Runnable {
    private buffer buffer;

    public consumer(buffer c) {
        super(5); // 5 cols
        buffer = c;
    }
    public void run() {
        // Implement an infinite loop for getting data from buffer
        // Get a data from buffer
        // Update the content with setText metod to "Got xxx"
        // Count down by using loop and sleep method

        while (true) {
            int no = buffer.get();
            setText("Got " + no);
            // Count down
            for (; no >= 0; no--) {
                repaint();
                try {
                    Thread.sleep(500); // currernt thread sleep 0.5s
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                setText(Integer.toString(no));
            }
        }
    }
}

