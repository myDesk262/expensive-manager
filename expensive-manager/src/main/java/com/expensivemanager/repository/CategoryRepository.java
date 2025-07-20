package com.expensivemanager.repository;

import com.expensivemanager.model.Category;
import com.expensivemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserOrUserIsNull(User user); // user's or global
    boolean existsByNameAndUser(String name, User user);
}
