/*
 * Copyright (c) 2021. Leafage All Right Reserved.
 */
package io.leafage.basic.assets.document;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

/**
 * Model class for posts
 *
 * @author liwenqiang 2020-10-06 22:09
 */
@Document(collection = "posts")
public class Posts extends BaseDocument {

    /**
     * 代码
     */
    @Indexed(unique = true)
    private String code;
    /**
     * 分类ID
     */
    @Field(name = "category_id")
    private ObjectId categoryId;
    /**
     * 标题
     */
    private String title;
    /**
     * 副标题
     */
    private String subtitle;
    /**
     * 封面
     */
    private String cover;
    /**
     * 标签
     */
    private Set<String> tags;
    /**
     * 点赞
     */
    private int likes;
    /**
     * 评论
     */
    private int comment;
    /**
     * 查看
     */
    private int viewed;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ObjectId getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(ObjectId categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }
}
