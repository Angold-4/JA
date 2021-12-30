//===----------------------------------------------------------------------===//
//
//                         JA
//
// Insert.java
//
// Identification: dbwork/dbinterface/Insert.java
//
// Usage: Link to db_312 database, and then let user insert data into it
// Last Modified : 2021.12.30 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Insert extends JFrame implements ActionListener {

    private JTextField nameT = new JTextField(50);
    private JTextField idT = new JTextField(50);
    private JTextField addressT = new JTextField(50);
    private PreparedStatement prepared;

    public Insert() throws ClassNotFoundException, SQLException {
	// jdbc : Java Database Connectivity
        Class.forName("com.mysql.jdbc.Driver"); // you can check the mysql-connector-java.jar to find the Driver
	// equal to
	// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	// load the class
    
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_312", "angold", "8616jw386");
        
        // Add code here
        // Create the PreparedStatement object to insert an student record
        prepared = con.prepareStatement("insert into student values(?,?,?)");

        JPanel p = new JPanel();
        this.getContentPane().add(p);
        p.setLayout(new BorderLayout());
        JPanel left = new JPanel();
        left.setLayout(new GridLayout(3, 1));
        p.add(left, BorderLayout.WEST);
        JPanel right = new JPanel();
        right.setLayout(new GridLayout(3, 1));
        p.add(right);
        left.add(new JLabel("Name:"));
        left.add(new JLabel("Student id:"));
        left.add(new JLabel("Address:"));
        right.add(nameT);
        right.add(idT);
        right.add(addressT);
        JPanel s = new JPanel();
        this.getContentPane().add(s, BorderLayout.SOUTH);
        s.setLayout(new BorderLayout());
        JButton submit = new JButton("insert");
        s.add(submit, BorderLayout.EAST);
        submit.addActionListener(this);
        pack();
        setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String st[]) throws Exception {
        Insert f = new Insert();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Input validation
        // Setup the PreparedStatement object with values input by user
        // Insert a new student record to database
        String message = "";
        String name = nameT.getText().trim();
        String id = idT.getText().trim();
        String address = addressT.getText().trim();
        if (name.length() == 0) {
            message += "Student name is blank.\n";
        }
        if (id.length() == 0) {
            message += "Student id is blank.\n";
        }
        if (address.length() == 0) {
            message += "Address is blank.\n";
        }
        if (message.length() == 0) {
            try {
                prepared.setString(1, id);
                prepared.setString(2, name);
                prepared.setString(3, address);
                prepared.executeUpdate();
                idT.setText("");
                nameT.setText("");
                addressT.setText("");
            } catch (Exception ee) {
		// id is the primary key
                JOptionPane.showMessageDialog(this, "Duplicated student id.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

