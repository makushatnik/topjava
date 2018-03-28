package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private static final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private static AtomicInteger counter = new AtomicInteger(0);

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    {
        save(new User(USER_ID, "User", "user@example.com", "123", Role.ROLE_USER));
        save(new User(ADMIN_ID, "Admin", "admin@example.com", "123456", Role.ROLE_ADMIN));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) == null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        return repository.put(user.getId(), user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values().stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Objects.requireNonNull(email);
        return repository.values().stream().filter(x -> x.getEmail().equals(email)).findFirst().orElse(null);
    }
}
