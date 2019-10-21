package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("getAll")
    public Map<String, Object> getAll(Integer page, Integer rows) {
        Map<String, Object> map = articleService.queryAll(page, rows);
        return map;
    }

    @RequestMapping("add")
    public void getAll(Article article) {
        articleService.add(article);
    }

    @RequestMapping("update")
    public void update(Article article) {
        articleService.update(article);
    }

    @RequestMapping("queryByes")
    public List<Article> queryByes(String esvalue) {
        System.out.println(esvalue);
        List<Article> articles = articleService.queryByes(esvalue);
        for (Article article : articles) {
            System.out.println(article);
        }
        return articles;
    }
}
