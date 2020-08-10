package com.goodguy.blog.service;

import com.goodguy.blog.dao.FriendDAO;
import com.goodguy.blog.entity.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {
    @Autowired
    FriendDAO friendDAO;
    @Autowired
    RedisService redisService;

    public List<Friend> list() {
        List<Friend> friend;
        Object friendCache = redisService.get("friend");
        if(friendCache == null){
            friend = friendDAO.findAll();
            redisService.set("friend", friend);
        }else{
            friend = (List<Friend>) friendCache;
        }
        return friend;
    }

    public void addOrUpdate(Friend friend) {
        redisService.delete("friend");
        friendDAO.save(friend);
    }

    public void deleteById(Friend friend) {
        redisService.delete("friend");
        friendDAO.deleteById(friend.getId());
    }
}
