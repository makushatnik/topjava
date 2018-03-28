package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if (!repository.delete(id, userId)) throw new NotFoundException("");
    }

    @Override
    public void deleteAll(int userId) throws NotFoundException {
        List<Meal> meals = new ArrayList<>(repository.getAll(userId));
        if (meals.isEmpty()) throw new NotFoundException("");
        for (Meal meal : meals)
            repository.delete(meal.getId(), userId);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        Meal meal = repository.get(id, userId);
        if (meal != null) return meal;
        throw new NotFoundException("");
    }

    @Override
    public void update(Meal meal, int userId) {
        repository.save(meal, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return (List<Meal>) repository.getAll(userId);
    }

    @Override
    public List<Meal> getBetweenDateTimes(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return new ArrayList<>(repository.getBetween(startDate, endDate, userId));
    }
}