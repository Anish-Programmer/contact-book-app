<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/addContact.css">
<title>Add a  new Contact</title>
</head>
<body>

 <h2>Add New Contact</h2>

    <form action="${pageContext.request.contextPath}/contact/insert" method="post">
        <div>
            <label for="name">Name:</label>
            <input type="text" name="name" id="name" required>
        </div>

        <div>
            <label for="phone">Phone:</label>
            <input type="text" name="phone" id="phone" required>
        </div>

        <div>
            <label for="email">Email:</label>
            <input type="email" name="email" id="email" required>
        </div>

        <div>
            <label for="address">Address:</label>
            <input type="text" name="address" id="address" required>
        </div>

        <button type="submit">Add Contact</button>
        <a href="${pageContext.request.contextPath}/contact/list">
            <button type="button">Back to Contact List</button>
        </a>
         
    </form>

</body>
</html>