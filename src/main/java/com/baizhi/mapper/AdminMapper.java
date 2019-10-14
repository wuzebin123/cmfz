package com.baizhi.mapper;

import com.baizhi.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    Admin find(String username);

    Admin findOne(@Param("username") String username, @Param("password") String password);
}
