package com.baizhi.serviceimpl;

import com.baizhi.entity.Article;
import com.baizhi.mapper.ArticleMapper;
import com.baizhi.service.ArticleService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
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

    @Override
    public List<Article> queryByes(String val) {
        System.out.println("-----------------");
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:red'>");
        field.postTags("<span>");
        field.requireFieldMatch(false);

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withIndices("cmfz")
                .withTypes("article")
                .withQuery(QueryBuilders.queryStringQuery(val).analyzer("ik_max_word").field("title").field("content"))
                .withHighlightFields(field)
                .build();

        AggregatedPage<Article> articles = elasticsearchTemplate.queryForPage(build, Article.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                ArrayList<Article> list = new ArrayList<>();
                SearchHits hits = searchResponse.getHits();
                SearchHit[] hits1 = hits.getHits();
                for (SearchHit searchHit : hits1) {
                    Article article = new Article();
                    article.setId(searchHit.getSourceAsMap().get("id").toString());
                    article.setAuthor(searchHit.getSourceAsMap().get("author").toString());
                    article.setTitle(searchHit.getSourceAsMap().get("title").toString());
                    article.setContent(searchHit.getSourceAsMap().get("content").toString());
                    article.setCreate_date(new Date(Long.valueOf(searchHit.getSourceAsMap().get("create_date").toString())));
                    article.setStatus(searchHit.getSourceAsMap().get("status").toString());
                    Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                    if (highlightFields.get("title") != null) {
                        String title = highlightFields.get("title").getFragments()[0].toString();
                        article.setTitle(title);
                    }
                    if (highlightFields.get("content") != null) {
                        String content = highlightFields.get("content").getFragments()[0].toString();
                        article.setContent(content);
                    }
                    list.add(article);
                }
                return new AggregatedPageImpl<T>((List<T>) list);
            }
        });
        return articles.getContent();
    }


}
