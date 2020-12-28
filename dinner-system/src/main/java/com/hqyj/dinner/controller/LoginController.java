package com.hqyj.dinner.controller;

import com.hqyj.dinner.entity.User;
import com.hqyj.dinner.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Program: dinner
 * Description: 登录注册
 * Author: Luo
 * Date: 2020-10-20 00:20
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("{url}")
    public String redirect(@PathVariable("url") String url){
        return url;
    }

    @RequestMapping("loginForm")
    public String loginForm(User user, Model model) {
        return loginService.login(user, model);
    }

    @ResponseBody
    @RequestMapping("register")
    public HashMap<String, Object> register(User user) {
        return loginService.register(user);
    }

    @RequestMapping("logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
