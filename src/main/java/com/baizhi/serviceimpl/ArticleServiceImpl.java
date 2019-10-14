package com.baizhi.serviceimpl;

import com.baizhi.entity.Article;
import com.baizhi.mapper.ArticleMapper;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAll(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        //获取数据库中的起始条
        Integer begin = (page - 1) * rows;
        //查询出轮播图列表
        List<Article> list = articleMapper.queryAll(begin, rows);
        //查询出总条数
        Integer records = articleMapper.getCount();
        //总页数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        map.put("records", records);
        map.put("page", page);
        map.put("rows", list);
        return map;
    }

    @Override
    public void add(Article article) {
        String s = UUID.randomUUID().toString();
        article.setId(s);
        article.setCreate_date(new Date());
        articleMapper.add(article);
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

}
