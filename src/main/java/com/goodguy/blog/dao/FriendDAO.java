package com.goodguy.blog.dao;

import com.goodguy.blog.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendDAO extends JpaRepository<Friend,Integer> {

}
