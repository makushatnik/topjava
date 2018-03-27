<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Meal</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
    <h2><a href="meals.jsp">Cancel</a></h2>
    <h3>Edit Meal</h3>
    <span>Id = ${meal.id}</span>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id > 0 ? meal.id : -1}"/>
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" name="dateTime" value="${meal.dateTime}"/></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" name="description" value="${meal.description}" size="40"/></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><input type="number" name="calories" value="${meal.calories}" min="1" max="10000" step="1"/></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form>
</body>
</html>