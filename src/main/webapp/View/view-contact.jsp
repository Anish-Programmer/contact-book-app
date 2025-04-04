<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Model.Contact" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Contact</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/viewContactStyle.css">
</head>
<body>

    <h2>Contact Details</h2>

    <%
        // Retrieve the contact object from the request scope
        Contact contact = (Contact) request.getAttribute("contact");

        if (contact == null) {
    %>
        <p style="color: red;">Error: Contact not found.</p>
        <a href="${pageContext.request.contextPath}/contact/list">Back to Contact List</a>
    <%
        } else {
    %>

    <table border="1">
        <tr>
            <th>Name:</th>
            <td><%= contact.getName() %></td>
        </tr>
        <tr>
            <th>Phone:</th>
            <td><%= contact.getPhone() %></td>
        </tr>
        <tr>
            <th>Email:</th>
            <td><%= contact.getEmail() %></td>
        </tr>
        <tr>
            <th>Address:</th>
            <td><%= contact.getAddress() %></td>
        </tr>
    </table>

    <br>

    <!-- Navigation Buttons -->
    <a href="${pageContext.request.contextPath}/contact/list">
        <button type="button">Back to Contact List</button>
    </a>
   
  

    <%
        }
    %>
    


</body>
</html>
