package com.goodguy.blog.dao;

import com.goodguy.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User findById(int id);
    User getByUsernameAndPassword(String username, String password);
}
