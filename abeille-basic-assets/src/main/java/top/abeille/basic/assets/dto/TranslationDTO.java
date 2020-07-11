/*
 * Copyright © 2010-2019 Abeille All rights reserved.
 */
package top.abeille.basic.assets.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO class for TranslationInfo
 *
 * @author liwenqiang
 */
public class TranslationDTO implements Serializable {

    private static final long serialVersionUID = 2348596129394113262L;
    /**
     * 标题
     */
    @NotBlank
    private String title;
    /**
     * 原文
     */
    private String original;
    /**
     * 内容
     */
    @NotBlank
    private String content;
    /**
     * 目录
     */
    private String catalog;
    /**
     * 原文连接
     */
    @NotBlank
    private String originalUrl;
    /**
     * 修改人
     */
    @NotBlank
    private String modifier;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}
