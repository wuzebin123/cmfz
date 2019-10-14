package com.baizhi.mapper;

import com.baizhi.entity.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PictureMapper {
    Integer count();

    List<Picture> findAll(@Param("start") Integer start, @Param("rows") Integer rows);

    void insert(Picture picture);

    void delete(String id);

    void update(Picture picture);

    Picture findOne(String id);

    List<Picture> select();
}
