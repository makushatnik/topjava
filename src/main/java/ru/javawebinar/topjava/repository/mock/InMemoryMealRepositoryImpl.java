package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.ADMIN_ID;
import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.USER_ID;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private static AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510), USER_ID);

        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 500), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 510), ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Integer mealId = meal.getId();
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else if (get(mealId, userId) == null) {
            return null;
        }
        // treat case: update, but absent in storage
        //return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        meals.put(mealId, meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null ? null : meals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? Collections.emptyList() : meals.values().stream()
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getBetween(final LocalDateTime startDate, final LocalDateTime endDate, int userId) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        return getAll(userId).stream()
                .filter(um -> DateTimeUtil.isBetween(um.getDateTime().toLocalTime(),
                        startDate.toLocalTime(), endDate.toLocalTime()))
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }
}

