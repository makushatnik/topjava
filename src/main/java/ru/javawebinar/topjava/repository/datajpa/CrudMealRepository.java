package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=?1 AND m.userId=?2")
    int delete(int id, int userId);

    @Override
    @Transactional
    Meal save(Meal user);

    Optional<Meal> findByIdAndUserId(int id, int userId);

    List<Meal> findAll(int userId);

    @Query("SELECT * FROM Meal m WHERE m.dateTime>=?1 AND m.dateTime<=?2 AND m.userId=?3")
    List<Meal> findBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);
}
