package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        log.info("getAll");
        return MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.id()),
                AuthorizedUser.getCaloriesPerDay(), LocalTime.MIN, LocalTime.MAX);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return service.create(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, AuthorizedUser.id());
    }

    public void deleteAll() {
        log.info("deleteAll");
        service.deleteAll(AuthorizedUser.id());
    }

    public void update(Meal meal) {
        int userId = AuthorizedUser.id();
        log.info("update {} with id={}", meal, userId);
        service.update(meal, userId);
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("getBetween {} and {}", startDate, endDate);
        return MealsUtil.getFilteredWithExceeded(service.getBetweenDates(startDate, endDate, AuthorizedUser.id()),
                AuthorizedUser.getCaloriesPerDay(), startTime, endTime);
    }
}