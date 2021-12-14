//===----------------------------------------------------------------------===//
//
//                         JA
//
// frame.java
//
// Identification: buffercon/frame.java
//
// Usage: A JFrame (from swing), main entry of the hole buffercon
// which will create a JFrame, set the buffer, consumer, 
// and handle the click input to the producer
//
// Last Modified : 2021.12.15 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//

package buffercon;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class frame extends JFrame {
    private buffer buffer; //the buffer
    private consumer consumer[]=new consumer[3]; //the three consumers
    private JTextField input=new JTextField(20); //the input textfield
    private JButton button=new JButton("Put integers");


    public frame() {
        buffer=new buffer();
        buffer.setEnabled(false);
        
	// panel north : Buffer content: 
        JPanel panel1=new JPanel();
        panel1.add(new JLabel("Buffer content: "));
        panel1.add(buffer);
        add(panel1,BorderLayout.NORTH);

        JPanel panel2=new JPanel();
        panel2.setLayout(new GridLayout(2,3));

        for (int i = 0; i < 3; i++) {
            panel2.add(new JLabel("Consumer " + (i+1)));
        }

        for (int i = 0; i < 3; i++) {
            consumer[i] = new consumer(buffer);
            Thread th = new Thread(consumer[i]);
            th.start();

            consumer[i].setEnabled(false); // false means:
	    // 1. not selectable (cannot copy)
	    // 2. cannot change the TextField's contents directly
            panel2.add(consumer[i]);
        }
        add(panel2);

        JPanel panel3 = new JPanel();
        panel3.add(input);
        panel3.add(button);
        add(panel3, BorderLayout.SOUTH);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int no = Integer.parseInt(input.getText().trim()); // trim remove the font and back spaces
                    producer p=new producer(buffer, no);
                    p.start();
                    input.setText("");
                }
                catch (Exception ee) {                    
                }
            }
        });
    }
    
    public static void main(String st[]) {
        frame f = new frame();
    }
}
 
