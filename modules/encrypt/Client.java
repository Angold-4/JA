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
// Last Modified : 2021.12.31 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//

package encrypt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Client extends JFrame implements Runnable {

    private JTextArea messageArea = new JTextArea(10, 80);
    private JTextField inputArea = new JTextField(80);
    private JTextField host = new JTextField(40);
    private JTextField port = new JTextField(10);
    private JTextField id = new JTextField(10);
    private JButton connect = new JButton("connect");
    private Cipher encryptCipher, decryptCipher;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket socket;
    private JButton submit = new JButton("submit");
    private SecretKey key;

    public Client() {
        JPanel north = new JPanel();
        north.add(new JLabel("ID:"));
        north.add(id);
        add(north, BorderLayout.NORTH);
        north.add(new JLabel("Host:"));
        north.add(host);
        north.add(new JLabel("Port:"));
        north.add(port);
        north.add(connect);
        JScrollPane s = new JScrollPane(messageArea);
        add(s);
        messageArea.setEditable(false);
        JPanel south = new JPanel();
        add(south, BorderLayout.SOUTH);
        south.add(inputArea);
        south.add(submit);
        pack();
        setVisible(true);
        inputArea.setEnabled(false);
        submit.setEnabled(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        key = GenerateKey.genkey("comps312");
        try {
	    // Generates a Cipher object that implements the specified transformation.
            encryptCipher = Cipher.getInstance("DES");
            decryptCipher = Cipher.getInstance("DES");
        } catch (Exception e) {
            e.printStackTrace();
        }
        connect.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    if (connect.getText().equals("connect")) {
                        socket = new Socket(host.getText().trim(), Integer.parseInt(port.getText().trim()));
                        output = new ObjectOutputStream(socket.getOutputStream());
                        input = new ObjectInputStream(socket.getInputStream());
                        connect.setText("disconnect");
                        inputArea.setEnabled(true);
                        id.setEnabled(false);
                        host.setEnabled(false);
                        port.setEnabled(false);
                        inputArea.setEnabled(true);
                        submit.setEnabled(true);
                        Thread th = new Thread(Client.this);
                        th.start();
                    } else {
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
                }
            }
        });
        submit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // Initialize encryptCipher for encryption
                // Get message from inputArea with getText method and add the id at the beginning
                // Encrypt the message with encryptCipher
                // Send the encrypted message to server with writeObject method
                // Empty inputArea with setText method and an empty string
                byte data[] = null;
                try {
                    encryptCipher.init(Cipher.ENCRYPT_MODE, key); // same key
                    String st = id.getText() + ":" + inputArea.getText();
                    data = encryptCipher.doFinal(st.getBytes());
                    output.writeObject(data);
                    output.flush();
                    inputArea.setText("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void run() {
        try {
            // Initialize decryptCipher for decryption
            // Use infinite loop to receive income message from server
            // Get encrypted message from server
            // Decrypt message
            // Display the message in messageArea with append method
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
            while (true) {
                byte[] data = (byte[]) input.readObject();
                String st = new String(decryptCipher.doFinal(data));
                messageArea.append(st + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String st[]) {
        Client c = new Client();
    }
}
