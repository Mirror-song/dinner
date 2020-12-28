package com.hqyj.dinner.service.impl;

import com.hqyj.dinner.service.UserService;
import com.hqyj.dinner.util.MdFive;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Program: dinner
 * Description:
 * Author: Luo
 * Date: 2020-10-20 00:48
 */
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    MdFive mdFive;
    @Autowired
    private UserService userService;

    @Test
    void selectUserByUserName() {
        System.out.println(userService.selectUserByUserName("admin"));
//        System.out.println(mdFive.encrypt("123", "admin"));
    }

    @Test
    void selectRole() {
        List<String> userList = userService.selectRole("admin");
        System.out.println(userList);
//        userList.forEach(System.out::println);
    }
    @Test
    void selectAllUser() {
        System.out.println(userService.queryAllUser());

//        System.out.println(mdFive.encrypt("123", "admin"));
    }
}