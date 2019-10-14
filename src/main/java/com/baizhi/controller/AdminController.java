package com.baizhi.controller;


import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.ValidationCodeServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    ValidationCodeServlet validationCodeServlet;

    @ResponseBody
    @RequestMapping("findOne")
    public String findOne(String username, String password, HttpSession session, String code) {
        String s = "";
        String validationCode = (String) session.getAttribute("validationCode");
        if (validationCode.equals(code)) {
            Admin admin = adminService.find(username);
            if (admin != null) {
                Admin one1 = adminService.findOne(username, password);
                if (one1 != null) {
                    session.setAttribute("admin", one1);
                    s = "成功登录";
                } else {
                    s = "密码错误，请检查后登录";
                }
            } else {
                s = "用户名错误，请检查后登录";
            }
        } else {
            s = "验证码错误";
        }
        return s;
    }

    @ResponseBody
    @RequestMapping("vc")
    public void vc(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        validationCodeServlet.init();
        validationCodeServlet.doPost(request, response);
        validationCodeServlet.destroy();
    }

    @RequestMapping("cancellation")
    public String cancellation(HttpSession session) {
        session.invalidate();
        return "redirect:/login/login.jsp";
    }
}
