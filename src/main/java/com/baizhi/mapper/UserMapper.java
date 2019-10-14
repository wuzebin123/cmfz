package com.baizhi.mapper;

import com.baizhi.entity.UserMap;

import java.util.List;

public interface UserMapper {
    List<UserMap> findAll();

    List<Integer> select();
}
