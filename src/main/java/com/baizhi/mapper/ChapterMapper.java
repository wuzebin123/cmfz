package com.baizhi.mapper;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterMapper {
    List<Chapter> findAll(@Param("start") Integer start, @Param("rows") Integer rows, @Param("id") String id);

    Integer count(String id);

    void insert(Chapter chapter);

    void delete(String id);

    void update(Chapter chapter);

    Chapter findOne(String id);
}
