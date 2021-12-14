//===----------------------------------------------------------------------===//
//
//                         JA
//
// client.java
//
// Identification: serverc/client.java
//
// Usage: Create a client process (although it is implements runnable) with JFrame
// and try to create a socket to server, then it will do 2 things
// 1. if there is a submit event (click the button), then send 
// a socket message from inputArea to socket.OutputSteam.
// 2. a infinite while loop check whether the socket.InputStream has message
// if there is, read it line by line to the message area.
//
//
// Last Modified : 2021.12.15 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//


package serverc;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class client extends JFrame implements Runnable {

    private JTextArea messageArea = new JTextArea(10, 80);
    private JTextField inputArea = new JTextField(80);
    private JTextField host = new JTextField(40);
    private JTextField port = new JTextField(10);
    private JTextField id = new JTextField(10);
    private JButton connect = new JButton("connect");
    private DataInputStream input;
    private DataOutputStream output;
    private Socket socket;
    private JButton submit = new JButton("submit");

    public client() {
        JPanel north = new JPanel();
        north.add(new JLabel("ID:"));
        north.add(id);
        add(north, BorderLayout.NORTH);
        north.add(new JLabel("Host:"));
        north.add(host);
        north.add(new JLabel("Port:"));
        north.add(port);
        north.add(connect);
        javax.swing.JScrollPane s = new javax.swing.JScrollPane(messageArea);
        add(s);
        messageArea.setEditable(false);
        JPanel south = new JPanel();
        add(south, BorderLayout.SOUTH);
        south.add(inputArea);
        south.add(submit);
        pack();
        setVisible(true);
        inputArea.setEnabled(false); // only can input after connection
        submit.setEnabled(false);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	    connect.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    if (connect.getText().equals("connect")) {
			// if wants to connect
			// create a new socket and try to connect with it
                        socket = new Socket(host.getText().trim(), Integer.parseInt(port.getText().trim()));
                        output = new DataOutputStream(socket.getOutputStream());
                        input = new DataInputStream(socket.getInputStream());
                        connect.setText("disconnect");
                        inputArea.setEnabled(true);
                        id.setEnabled(false);
                        host.setEnabled(false);
                        port.setEnabled(false);
                        Thread th = new Thread(client.this);
                        inputArea.setEnabled(true);
                        submit.setEnabled(true);
                        th.start();
                    } else {
			// if wants to disconnect
                        output.close();
                        input.close();
                        socket.close();
                        connect.setText("connect");
                        inputArea.setEnabled(false);
                        id.setEnabled(true);
                        host.setEnabled(true);
                        port.setEnabled(true);
                        inputArea.setEnabled(false);
                        submit.setEnabled(false);
                    }

                } catch (Exception ee) {
		    System.out.println("Cannot Connect to Server!");
                }
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get message from inputArea with getText method and add the id at the beginning
                    // Send the message to server
                    // Empty inputArea with setText method with empty string
                    String msg = id.getText() + ":" + inputArea.getText();
                    output.writeUTF(msg);
                    output.flush(); // force sent the data in buffer to the server
                    inputArea.setText("");
                } catch (Exception ex) {
                    Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void run() {
        // Use infinite loop to receive income message from server
        // Get message from server
        // Display the message in messageArea with append method
        while (true) {
            try {
		// just keep trying receve the input (a while loop)
                String msg = input.readUTF();
                messageArea.append(msg + "\n");
            } catch (Exception e) {
                break;
            }
        }
    }

    public static void main(String st[]) {
        client c = new client(); // create a new client
    }
}
 
