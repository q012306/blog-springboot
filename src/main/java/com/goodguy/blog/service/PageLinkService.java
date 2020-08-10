package com.goodguy.blog.service;

import com.goodguy.blog.dao.PageLinkDAO;
import com.goodguy.blog.entity.PageLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageLinkService {
    @Autowired
    PageLinkDAO pageLinkDAO;
    @Autowired
    RedisService redisService;

    public List<PageLink> list() {
        List<PageLink> pageLinks;
        Object pageLinkCache = redisService.get("PageLink");
        if(pageLinkCache == null){
            pageLinks = pageLinkDAO.findAll();
            redisService.set("PageLink", pageLinks);
        }else{
            pageLinks = (List<PageLink>) pageLinkCache;
        }
        return pageLinks;
    }

    public void addOrUpdate(PageLink pageLink) {
        redisService.delete("PageLink");
        pageLinkDAO.save(pageLink);
    }

    public void deleteById(PageLink pageLink) {
        redisService.delete("PageLink");
        pageLinkDAO.deleteById(pageLink.getId());
    }
}
