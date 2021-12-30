<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ page import="java.sql.*" %> 
<%@ page import="jspinterface.MyConnection.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Insert</title>
    </head>
    <body>
        <%
            // Create and setup PreparedStatement object for searching all products
            PreparedStatement prepared = connection.statement("insert into student (id, name, address) values(?,?,?)");

            // Add code here
            // Obtain the three parameters from the client
            // Input validation
            // Setup the PreparedStatement object
            // Update db

            String name = request.getParamter("name");
            String id = request.getParameter("id");
            String address = request.getParameter("address");

            String message = "";
            if (name == null || name.length() == 0) {
               message += "Student name is blank.<br>";
            }
            if (id == null || id.length() == 0) {
                message += "Student id is blank.<br>";
            }
            if (address == null || address.length() == 0) {
                message += "Address is blank.<br>";
            }
            if (message.length() == 0) {
                try {
                    prepared.setString(1, id);
                    prepared.setString(2, name);
                    prepared.setString(3, address);
                    prepared.executeUpdate();

                    message = "Student " + name + " (" + id + ") has been stored";
                } catch (Exception ee) {
                    message += "Duplicated student id.<br>";
                }
            } else {
                message = "Insert Error<br>" + message;
            }
        %>
        
        <%=message%>
    </body>
</html>
 
