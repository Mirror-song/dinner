package com.hqyj.dinner.service;

import com.hqyj.dinner.entity.User;
import org.springframework.ui.Model;

import java.util.HashMap;

/**
 * Program: dinner
 * Description: 登录注册接口
 * Author: Luo
 * Date: 2020-10-20 13:43
 */
public interface LoginService {
    String login(User user, Model model);

    HashMap<String, Object> register(User user);
}
