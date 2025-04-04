<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Contact" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contact Book</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/contactListStyle.css">
</head>
<body>

    <div class="container">
        <h1>Contact List</h1>

        <div>
        	 <a href="${pageContext.request.contextPath}/View/search-contact.jsp">
                <button> Search Contact </button>
            </a>
        </div>

        <!-- Add Contact Button -->
        <a href="${pageContext.request.contextPath}/contact/new" class="btn">Add New Contact</a>

        <!-- Contacts Table -->
        <table>
            <tr>
                <th>Name</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>

            <%
                // Retrieve contact list from request scope
                List<Contact> contactList = (List<Contact>) request.getAttribute("contactList");

                if (contactList != null && !contactList.isEmpty()) {
                    for (Contact contact : contactList) {
            %>
            <tr>
                <td><%= contact.getName() %></td>
                <td><%= contact.getPhone() %></td>
                <td><%= contact.getEmail() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/contact/view?id=<%= contact.getId() %>">View</a> |
                    <a href="${pageContext.request.contextPath}/contact/edit?id=<%= contact.getId() %>">Edit</a> |
                    <a href="${pageContext.request.contextPath}/contact/delete?id=<%= contact.getId() %>"
                       onclick="return confirm('Are you sure you want to delete this contact?');">Delete</a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="4">No contacts found.</td>
            </tr>
            <%
                }
            %>
        </table>
        
        <a href="${pageContext.request.contextPath}/">
                <button>Back to Home </button>
            </a>
    </div>

</body>
</html>
