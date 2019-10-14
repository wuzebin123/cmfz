package com.baizhi.controller;

import com.baizhi.entity.UserMap;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("findAll")
    public List<UserMap> findAll() {
        List<UserMap> all = userService.findAll();
        return all;
    }

    @RequestMapping("query")
    public List<Integer> query() {
        List<Integer> query = userService.query();
        return query;
    }

    @RequestMapping("aaa")
    public void aaa(String aa) {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-1d636fd677f043a183ea7b2437aba87b");
        goEasy.publish("164channel", aa);
    }
}
