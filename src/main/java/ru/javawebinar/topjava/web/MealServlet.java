package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);

    private static MealRepository repository;

    @Override
    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        final Map<String, String[]> params = request.getParameterMap();
//
//        final int id = params.containsKey("id") ? Integer.parseInt(params.get("id")[0]) : 0;
//        String act = params.containsKey("act") ? params.get("act")[0] : "no actions";
//        log.debug("ID = " + id);
//        log.debug("Act = " + act);
//        if (!act.isEmpty()) {
//            if (act.equals("create")) {
//                request.getRequestDispatcher("/edit.jsp").forward(request, response);
//            } else {
//                if (id > 0) {
//                    if (act.equals("delete")) {
//                        for (Meal meal : MealsUtil.MEAL_LIST) {
//                            if (meal.getId() == id)
//                                MealsUtil.MEAL_LIST.remove(meal);
//                        }
//                        List<MealWithExceed> mealList = MealsUtil.getWithExceed(2000);
//                        request.setAttribute("mealList", mealList);
//                        request.getRequestDispatcher("/meals.jsp").forward(request, response);
//                    } else if (act.equals("edit")) {
//                        Meal meal = null;
//                        for (Meal curMeal : MealsUtil.MEAL_LIST) {
//                            if (curMeal.getId() == id) meal = curMeal;
//                        }
//                        request.setAttribute("meal", meal);
//                        request.getRequestDispatcher("/edit.jsp").forward(request, response);
//                    }
//                }
//            }
//        } else {
//            List<MealWithExceed> mealList = MealsUtil.getWithExceed(2000);
//            request.setAttribute("mealList", mealList);
//            request.getRequestDispatcher("/meals.jsp").forward(request, response);
//        }
        String act = request.getParameter("act");
        if (act == null) {
            LOG.info("getAll");
            request.setAttribute("mealList", MealsUtil.getWithExceed(repository.getAll(), 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (act.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            repository.delete(id);
            response.sendRedirect("meals");
        } else {
            final Meal meal = act.equals("create") ? new Meal(LocalDateTime.now(), "", 1000) :
                    repository.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("doPost");
        request.setCharacterEncoding("UTF-8");
//        final Map<String, String[]> params = request.getParameterMap();
//        //final int id = params.containsKey("id") ? Integer.parseInt(params.get("id")[0]) : 0;
//        String strId = request.getParameter("id");
//        final int id = strId.isEmpty() ? -1 : Integer.parseInt(strId);
//        LOG.debug("Id = " + id);
//        final LocalDateTime dateTime = //params.containsKey("dateTime") ?
//                //LocalDateTime.from(formatter.parse(params.get("dateTime")[0])) :
//                LocalDateTime.now();
//        final String description = params.containsKey("description") ? params.get("description")[0] : "";
//        final int calories = params.containsKey("calories") ? Integer.parseInt(params.get("calories")[0]) : 0;
//
//        LOG.debug("Descr = " + description);
//        LOG.debug("Calories = " + calories);
//        LOG.debug("Date = " + dateTime);
//
//        if (id > 0) {
//            Meal found = null;
//            for (Meal meal : MealsUtil.MEAL_LIST) {
//                if (meal.getId() == id) {
//                    found = meal;
//                    break;
//                }
//            }
//            if (found != null) {
//                MealsUtil.MEAL_LIST.remove(found);
//                MealsUtil.MEAL_LIST.add(new Meal(dateTime, description, calories));
//            } else {
//                MealsUtil.MEAL_LIST.add(new Meal(dateTime, description, calories));
//            }
//        } else {
//            MealsUtil.MEAL_LIST.add(new Meal(dateTime, description, calories));
//        }
//
//        response.sendRedirect("meals.jsp");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        repository.save(meal);
        response.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
