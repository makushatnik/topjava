package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList,
                LocalTime.of(7, 0), LocalTime.of(23,0), 2000);
        for (UserMealWithExceed userMeal : list) {
            System.out.println(userMeal);
        }
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(
            List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> res = new ArrayList<>();
        HashMap<LocalDate, Integer> tmp = new HashMap<>();

        for (UserMeal meal : mealList) {
            LocalDate curDate = meal.getDateTime().toLocalDate();
            tmp.put(curDate, tmp.getOrDefault(curDate, 0) + meal.getCalories());
        }
        for (Map.Entry<LocalDate, Integer> entry : tmp.entrySet()) {
            for (UserMeal meal : mealList) {
                if (!meal.getDateTime().toLocalDate().equals(entry.getKey())) continue;

                if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    res.add(new UserMealWithExceed(
                            meal.getDateTime(), meal.getDescription(),
                            meal.getCalories(), entry.getValue() > caloriesPerDay
                    ));
                }
            }
        }

        return res;
    }
}
