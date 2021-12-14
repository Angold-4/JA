//===----------------------------------------------------------------------===//
//
//                         JA
//
// buffer.java
//
// Identification: buffercon/buffer.java
//
// Usage: the api of buffer object
//
// Last Modified : 2021.12.15 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//


package buffercon;

import java.util.ArrayList;
import javax.swing.JTextField;


public class buffer extends JTextField {
    private final static int BUFFER_SIZE = 10;
    private ArrayList <Integer> data = new ArrayList<Integer>();

    public buffer() {
        super(30); // JTextField # of columns
    }

    // Display the content of the buffer
    public void showBufferContent() {
        String st= "";
        for (Integer i : data) {
            st += i + " ";
        }
        setText(st);
        repaint();
    }

    // Get an integer from the buffer
    public synchronized int get() {
        // Check if the buffer is empty with while loop
        // If so, invoke the wait method
        // Otherwise
        // - Get data from buffer
        // - Display the content of user interface with showBufferContent method
        // - Return the retrieved data

        while (data.isEmpty()) {
            try {
                wait();
           }
            catch (Exception e) {}
        }
        int re = data.remove(0);

        showBufferContent();
        return re;
    }

    // Put an integer to the buffer
    public synchronized void put(int i) {
        // Check if the buffer is full with while loop
        // If so, invoke the wait method
        // Otherwise
        // - Add data to buffer
        // - Notify all threads with notifyAll method
        // - Dispaly the content of user interface with showBufferContent method

        while (data.size()==BUFFER_SIZE) {
            try {
                wait();
            }
            catch (Exception e) {}
        }
        data.add(i);
        notifyAll();
        showBufferContent();
    }
}
 
