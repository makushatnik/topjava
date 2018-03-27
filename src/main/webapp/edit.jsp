<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Meal</title>
</head>
<body>
    <h3><a href="meals.jsp">Cancel</a></h3>
    <h2>Edit Meal</h2>
    <span>Id = ${meal.id}</span>
    <form method="post">
        <input type="hidden" name="id" value="${meal.id > 0 ? meal.id : -1}"/>
        <input type="datetime" name="dateTime" value="${meal.dateTime}"/>
        <input type="text" name="description" value="${meal.description}"/>
        <input type="number" name="calories" value="${meal.calories}" min="1" max="10000" step="1"/>
        <input type="submit" value="Send"/>
    </form>
</body>
</html>