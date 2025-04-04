<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/indexStyle.css">
</head>
<body>

    <div class="container">
        <header>
            <h1>Welcome to Contact Management System</h1>
            <p>Manage your contacts easily with our simple web application.</p>
        </header>

        <section class="menu">
            <a href="${pageContext.request.contextPath}/contact/list" class="button">View Contact List</a>
            <a href="${pageContext.request.contextPath}/contact/new" class="button">Add New Contact</a>
            <a href="${pageContext.request.contextPath}/View/search-contact.jsp" class="button">Search Contact</a>
        </section>

    </div>

</body>
</html>
