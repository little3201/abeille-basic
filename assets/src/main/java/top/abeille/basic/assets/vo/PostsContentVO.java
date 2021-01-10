/*
 * Copyright © 2010-2019 Abeille All rights reserved.
 */
package top.abeille.basic.assets.vo;

/**
 * Details VO class for ArticleInfo
 *
 * @author liwenqiang 2020-10-06 22:09
 */
public class PostsContentVO extends PostsVO {

    private static final long serialVersionUID = -3631862762916498067L;

    /**
     * 原文
     */
    private String original;
    /**
     * 内容
     */
    private String content;
    /**
     * 目录
     */
    private String catalog;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
}