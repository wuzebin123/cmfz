package com.baizhi.service;

import com.baizhi.entity.UserMap;

import java.util.List;

public interface UserService {
    List<UserMap> findAll();

    List<Integer> query();
}
