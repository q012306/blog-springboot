package com.goodguy.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "friend")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "昵称不能为空")
    private String friendName;
    @NotEmpty(message = "链接不能为空")
    private String friendPageUrl;
    @NotEmpty(message = "描述不能为空")
    private String friendAbstract;
    @NotEmpty(message = "头像不能为空")
    private String friendAvatar;
    private String friendBackGroundImgUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendPageUrl() {
        return friendPageUrl;
    }

    public void setFriendPageUrl(String friendPageUrl) {
        this.friendPageUrl = friendPageUrl;
    }

    public String getFriendBackGroundImgUrl() {
        return friendBackGroundImgUrl;
    }

    public void setFriendBackGroundImgUrl(String friendBackGroundImgUrl) {
        this.friendBackGroundImgUrl = friendBackGroundImgUrl;
    }

    public String getFriendAbstract() {
        return friendAbstract;
    }

    public void setFriendAbstract(String friendAbstract) {
        this.friendAbstract = friendAbstract;
    }

    public String getFriendAvatar() {
        return friendAvatar;
    }

    public void setFriendAvatar(String friendAvatar) {
        this.friendAvatar = friendAvatar;
    }
}
