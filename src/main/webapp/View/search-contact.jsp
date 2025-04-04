<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.Contact" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/searchContactStyle.css">
    <title>Search Contact by Name</title>
    
</head>
<body>

<h2>Search Contact by Name</h2>

<!-- Form to search for a contact by Name -->
<form action="<%= request.getContextPath() %>/contact/search" method="get">
    <label for="searchName">Search by Name:</label>
    <input type="text" id="searchName" name="name" placeholder="Enter contact name" required />
    
    <button type="submit">Search</button>
</form>

<% 
    // Check if there's an error message
    String error = (String) request.getAttribute("error");
    if (error != null) {
        if (error.equals("missingSearchParam")) {
%>
            <p style="color: red;">Please enter a name to search.</p>
<%  
        } else if (error.equals("notFound")) {
%>
            <p style="color: red;">No contact found with the given name. Please try again.</p>
<%  
        } else if (error.equals("unexpectedError")) {
%>
            <p style="color: red;">An unexpected error occurred. Please try again later.</p>
<%  
        }
    }

    // Check if a contact is found and display it
    Contact contact = (Contact) request.getAttribute("contact");
    if (contact != null) {
%>
        <h3>Contact Found</h3>
        <p><strong>Name:</strong> <%= contact.getName() %></p>
        <p><strong>Phone:</strong> <%= contact.getPhone() %></p>
        <p><strong>Email:</strong> <%= contact.getEmail() %></p>
        <p><strong>Address:</strong> <%= contact.getAddress() %></p>
<%  
    }
%>

 <a href="${pageContext.request.contextPath}/">
                <button>Back to Home </button>
            </a>

</body>
</html>
