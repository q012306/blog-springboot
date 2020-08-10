package com.goodguy.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "comment")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "关联文章号不能为空")
    private int commentArticleId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDate;
    private String commentIp;
    @NotEmpty(message = "用户名不能为空")
    private String commentUsername;
    @NotEmpty(message = "评论内容不能为空")
    private String commentContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentArticleId() {
        return commentArticleId;
    }

    public void setCommentArticleId(int commentArticleId) {
        this.commentArticleId = commentArticleId;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentIp() {
        return commentIp;
    }

    public void setCommentIp(String commentIp) {
        this.commentIp = commentIp;
    }

    public String getCommentUsername() {
        return commentUsername;
    }

    public void setCommentUsername(String commentUsername) {
        this.commentUsername = commentUsername;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
