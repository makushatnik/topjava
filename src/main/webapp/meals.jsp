<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {color: green;}
        .exceeded {color: red;}
    </style>
</head>
<body>
    <h2><a href="index.html">Home</a></h2>
    <h3>Meal list</h3>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Id</th>
            <th>Date</th>
            <th>Name</th>
            <th>Calories</th>
            <th>Exceed</th>
            <th>&nbsp;</th>
        </tr>
    <c:forEach var="meal" items="${mealList}">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr class="${meal.exceed == true ? 'exceeded' : 'normal'};">
            <td>${meal.id}</td>
            <td>
                <%= TimeUtil.toString(meal.getDateTime()) %>
                <%--${meal.dateTime.format(formatter)}--%>
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