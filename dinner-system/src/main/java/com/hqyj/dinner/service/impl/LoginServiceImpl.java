package com.hqyj.dinner.service.impl;

import com.hqyj.dinner.entity.User;
import com.hqyj.dinner.service.LoginService;
import com.hqyj.dinner.service.UserService;
import com.hqyj.dinner.util.MdFive;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;

/**
 * Program: dinner
 * Description: 登录注册实现
 * Author: Luo
 * Date: 2020-10-20 13:44
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private MdFive mdFive;

    @Autowired
    private UserService userService;

    @Override
    public String login(User user, Model model) {
        //创建主体，即访问应用的用户，在 Shiro 中使用 Subject 代表该用户
        Subject subject = SecurityUtils.getSubject();
        //创建用户凭证，即当前登录用户名和密码对象
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPwd());
        try {
            //登录，即身份验证
            subject.login(token);
//            subject.getSession().setAttribute("user",user);
            return "index";
        } catch (UnknownAccountException e) {
//            e.printStackTrace();
            model.addAttribute("info", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
//            e.printStackTrace();
            model.addAttribute("info", "密码错误!");
            return "login";
        }
    }

    @Override
    public HashMap<String, Object> register(User user) {
        HashMap<String, Object> map = new HashMap<>();
        User user_db = userService.selectUserByUserName(user.getUserName());
        if (user_db == null) {
            //md5加密，把用户名作为盐值
            String encrypt = mdFive.encrypt(user.getPwd(), user.getUserName());
            user.setPwd(encrypt);
            System.out.println(user);
            if (userService.save(user)) {
                map.put("info", "注册成功");
            } else {
                map.put("info", "注册失败");
            }
        } else {
            map.put("info", "用户已存在");
        }
        return map;
    }
}
