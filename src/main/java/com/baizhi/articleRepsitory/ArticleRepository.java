package com.baizhi.articleRepsitory;

import com.baizhi.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;


public interface ArticleRepository extends ElasticsearchCrudRepository<Article, String> {

}
