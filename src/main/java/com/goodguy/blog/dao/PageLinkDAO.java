package com.goodguy.blog.dao;

import com.goodguy.blog.entity.PageLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageLinkDAO extends JpaRepository<PageLink,Integer> {

}