package com.baizhi.serviceimpl;

import com.baizhi.entity.UserMap;
import com.baizhi.mapper.UserMapper;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserMap> findAll() {
        List<UserMap> all = userMapper.findAll();
        return all;
    }

    @Override
    public List<Integer> query() {
        List<Integer> list = new ArrayList<>();
        List<Integer> select = userMapper.select();
        for (Integer integer : select) {
            list.add(integer);
        }
        return list;
    }
}
