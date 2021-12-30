//===----------------------------------------------------------------------===//
//
//                         JA
//
// Show.java
//
// Identification: dbwork/dbinterface/Show.java
//
// Usage: Link to db_312 database, and then search for specific message
// Last Modified : 2021.12.30 Jiawei Wang
//
// Copyright (c) 2021 Angold-4
//
//===----------------------------------------------------------------------===//



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Show extends JFrame implements ActionListener {

    private JTextField idT = new JTextField(20);
    private JTextArea showArea = new JTextArea(15, 40);
    private PreparedStatement p1, p2;

    public Show() throws ClassNotFoundException, SQLException {
	// jdbc : Java Database Connectivity
        Class.forName("com.mysql.jdbc.Driver"); // you can check the mysql-connector-java.jar to find the Driver
	// equal to
	// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	// load the class
    
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_312", "angold", "8616jw386");

	p1 = con.prepareStatement("select name, address from student where id=?");
	p2 = con.prepareStatement("select id, title, yearofstudy, score from course, study where id=courseid and studentid=?");

	JPanel top = new JPanel();
	this.getContentPane().add(top, BorderLayout.NORTH);
	top.setLayout(new BorderLayout());
	JPanel topleft = new JPanel();
	top.add(topleft);
	JButton show = new JButton("show");
	top.add(show, BorderLayout.EAST);
	show.addActionListener(this); // button

	topleft.add(new JLabel("Student id:"));
	topleft.add(idT);
	showArea.setEditable(false);
	this.getContentPane().add(showArea);
	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	pack();
	setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	try {
	    showArea.setText("");

	    p1.setString(1, idT.getText().trim());
	    ResultSet re = p1.executeQuery();

	    if (re.next()) {
		String name = re.getString(1);
		String address = re.getString(2);
		showArea.setText("Name:" + name + "\nAddress:" + address + "\n");
		p2.setString(1, idT.getText().trim());
		ResultSet re2 = p2.executeQuery();

		while (re2.next()) {
		    String id = re2.getString(1);
		    String title = re2.getString(2);
		    String year = re2.getString(3);
		    float score = re2.getFloat(4);
		    showArea.append("Score of " + id + " " + title + "(" + year + ") is" + score + "\n");
		}
	    } else {
		JOptionPane.showMessageDialog(this, "Student not found", "Eror", JOptionPane.ERROR_MESSAGE);
	    }
	} catch (Exception ee) {
	    ee.printStackTrace();
	}
    }

    public static void main(String st[]) throws ClassNotFoundException, SQLException {
	Show s = new Show();
    }
}



