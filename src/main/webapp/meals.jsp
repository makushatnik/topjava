<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <table>
        <tr>
            <th>Id</th>
            <th>Date</th>
            <th>Name</th>
            <th>Calories</th>
            <th>Exceed</th>
            <th>&nbsp;</th>
        </tr>
    <c:forEach var="meal" items="${mealList}">
        <tr style="color: ${meal.exceed == true ? 'red' : 'green'};">
            <td>${meal.id}</td>
            <td>
                <!--javatime:parseLocalDateTime value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm" var="parsedDate"/>
                ${parsedDate}-->
                ${meal.dateTime.format(formatter)}
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.exceed}</td>
            <td>
                <a href="meals?id=${meal.id}&act=edit">Edit</a>
                <a href="meals?id=${meal.id}&act=delete">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </table>
    <a class="btn btn-default" href="meals?act=create">Create New</a>
</body>
</html>