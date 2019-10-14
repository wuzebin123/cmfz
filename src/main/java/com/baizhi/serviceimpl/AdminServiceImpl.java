package com.baizhi.serviceimpl;

import com.baizhi.entity.Admin;
import com.baizhi.mapper.AdminMapper;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin find(String username) {
        Admin admin = adminMapper.find(username);
        return admin;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin findOne(String username, String password) {
        Admin admin = adminMapper.findOne(username, password);
        return admin;
    }
}
