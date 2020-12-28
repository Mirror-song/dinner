package com.hqyj.dinner.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Program: dinner
 * Description:
 * Author: Luo
 * Date: 2020-10-23 19:49
 */
@SpringBootTest
class FoodControllerTest {

    @Autowired
    FoodController foodController;

    @Test
    void test(){
        final HashMap<String, Object> map = foodController.queryMenuFood(1, 5);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }

}