package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {
    Map<String, Object> queryAll(Integer page, Integer rows);

    void add(Article article);

    void update(Article article);
}
