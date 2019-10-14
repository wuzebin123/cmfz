package com.baizhi.mapper;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumMapper {
    List<Album> findAll(@Param("start") Integer start, @Param("rows") Integer rows);

    Integer count();

    void insert(Album album);

    void delete(String id);

    void update(Album album);

    Album findOne(String id);

    void updateCount(@Param("count") Integer count, @Param("id") String id);
}
