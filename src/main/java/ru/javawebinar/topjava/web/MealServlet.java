package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

//    private static List<Meal> meals = Arrays.asList(
//        new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
//        new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
//        new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
//        new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
//        new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
//        new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
//    );
    private static final List<Meal> meals = new CopyOnWriteArrayList<>();

    {
        meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        final Map<String, String[]> params = request.getParameterMap();

        final int id = params.containsKey("id") ? Integer.parseInt(params.get("id")[0]) : 0;
        String act = params.containsKey("act") ? params.get("act")[0] : "no actions";
        log.debug("ID = " + id);
        log.debug("Act = " + act);
        if (!act.isEmpty()) {
            if (act.equals("create")) {
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
            } else {
                if (id > 0) {
                    if (act.equals("delete")) {
                        for (Meal meal : meals) {
                            if (meal.getId() == id)
                                meals.remove(meal);
                        }
                        List<MealWithExceed> mealList = MealsUtil.getFilteredWithExceeded(meals,
                                LocalTime.of(0, 0), LocalTime.of(23, 0), 2000);
                        request.setAttribute("mealList", mealList);
                        request.setAttribute("formatter", formatter);
                        request.getRequestDispatcher("/meals.jsp").forward(request, response);
                    } else if (act.equals("edit")) {
                        Meal meal = null;
                        for (Meal curMeal : meals) {
                            if (curMeal.getId() == id) meal = curMeal;
                        }
                        request.setAttribute("meal", meal);
                        request.getRequestDispatcher("/edit.jsp").forward(request, response);
                    }
                }
            }
        } else {
            List<MealWithExceed> mealList = MealsUtil.getFilteredWithExceeded(meals,
                    LocalTime.of(0, 0), LocalTime.of(23, 0), 2000);
            request.setAttribute("mealList", mealList);
            request.setAttribute("formatter", formatter);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doPost");
        request.setCharacterEncoding("UTF-8");
        final Map<String, String[]> params = request.getParameterMap();
        //final int id = params.containsKey("id") ? Integer.parseInt(params.get("id")[0]) : 0;
        String strId = request.getParameter("id");
        final int id = strId.isEmpty() ? -1 : Integer.parseInt(strId);
        log.debug("Id = " + id);
        final LocalDateTime dateTime = //params.containsKey("dateTime") ?
                //LocalDateTime.from(formatter.parse(params.get("dateTime")[0])) :
                LocalDateTime.now();
        final String description = params.containsKey("description") ? params.get("description")[0] : "";
        final int calories = params.containsKey("calories") ? Integer.parseInt(params.get("calories")[0]) : 0;

        log.debug("Descr = " + description);
        log.debug("Calories = " + calories);
        log.debug("Date = " + dateTime);

        if (id > 0) {
            Meal found = null;
            for (Meal meal : meals) {
                if (meal.getId() == id) {
                    found = meal;
                    break;
                }
            }
            if (found != null) {
                meals.remove(found);
                meals.add(new Meal(dateTime, description, calories));
            } else {
                meals.add(new Meal(dateTime, description, calories));
            }
        } else {
            meals.add(new Meal(dateTime, description, calories));
        }

        response.sendRedirect("meals.jsp");
    }
}
