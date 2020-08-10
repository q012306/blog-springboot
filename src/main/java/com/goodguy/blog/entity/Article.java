package com.goodguy.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;

import javax.validation.constraints.NotEmpty;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

//ALTER TABLE `comment` ADD FOREIGN KEY (`comment_article_id`) REFERENCES `article`(`id`) ON DELETE CASCADE ON UPDATE CASCADE;
@Entity
@Table(name = "article")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "文章标题不能为空")
    private String articleTitle;
    @NotEmpty(message = "文章内容不能为空")
    @Lob
    private String articleContentMd;
    @NotEmpty(message = "文章概要不能为空")
    private String articleAbstract;
    @NotEmpty(message = "文章封面不能为空")
    private String articleCover;
    @Temporal(TemporalType.TIMESTAMP)
    private Date articleDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContentMd() {
        return articleContentMd;
    }

    public void setArticleContentMd(String articleContentMd) {
        this.articleContentMd = articleContentMd;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public String getArticleCover() {
        return articleCover;
    }

    public void setArticleCover(String articleCover) {
        this.articleCover = articleCover;
    }

    public Date getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(Date articleDate) {
        this.articleDate = articleDate;
    }
}