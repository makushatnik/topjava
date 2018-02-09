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
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(
            List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> res = new ArrayList<>();
        HashMap<LocalDate, Integer> tmp = new HashMap<>();

        int len = mealList.size();
        for (int i = 0; i < len; i++) {
            UserMeal curEl = mealList.get(i);
            LocalDate curDate = curEl.getDateTime().toLocalDate();
            if (tmp.containsKey(curDate)) {
                tmp.put(curDate, tmp.get(curDate) + curEl.getCalories());
            } else {
                tmp.put(curDate, curEl.getCalories());
            }
        }
        for (Map.Entry<LocalDate, Integer> entry : tmp.entrySet()) {
            if (entry.getValue() > caloriesPerDay) {
                for (int i = 0; i < len; i++) {
                    UserMeal curEl = mealList.get(i);
                    if (!curEl.getDateTime().toLocalDate().equals(entry.getKey())) continue;

                    if (curEl.getDateTime().toLocalDate() == entry.getKey()) {
                        res.add(new UserMealWithExceed(
                                curEl.getDateTime(), curEl.getDescription(),
                                curEl.getCalories(), true
                        ));
                    }
                }
            }
        }

        return res;
    }
}
