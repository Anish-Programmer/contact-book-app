<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Model.Contact" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Contact</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/editContactStyle.css">
</head>
<body>

    <h2>Edit Contact</h2>

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

    <!-- Display success or error messages -->
    <% if (request.getParameter("error") != null) { %>
        <p style="color: red;">Error: <%= request.getParameter("error") %></p>
    <% } %>

    <% if (request.getParameter("success") != null) { %>
        <p style="color: green;">Contact updated successfully!</p>
    <% } %>

    <!-- Edit Contact Form -->
    <form action="${pageContext.request.contextPath}/contact/update" method="post">
        <input type="hidden" name="id" value="<%= contact.getId() %>">

        <label for="name">Name:</label>
        <input type="text" name="name" id="name" value="<%= contact.getName() %>" required>

        <label for="phone">Phone:</label>
        <input type="text" name="phone" id="phone" value="<%= contact.getPhone() %>" required>

        <label for="email">Email:</label>
        <input type="email" name="email" id="email" value="<%= contact.getEmail() %>" required>

        <label for="address">Address:</label>
        <input type="text" name="address" id="address" value="<%= contact.getAddress() %>" required>

        <button type="submit">Update Contact</button>
        <a href="${pageContext.request.contextPath}/contact/list">
            <button type="button">Back to Contact List</button>
        </a>
        
         <a href="${pageContext.request.contextPath}/">
                <button>Back to Home </button>
            </a>
    </form>

    <%
        }
    %>

</body>
</html>
