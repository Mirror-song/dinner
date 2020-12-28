package com.hqyj.dinner;


import com.hqyj.dinner.entity.OrderDetail;
import com.hqyj.dinner.service.OrderDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@SpringBootTest
class DinnerApplicationTests {
    @Resource
    OrderDetailService orderDetailService;

    @Test
    void contextLoads() {
        Double pay = 0.0;
        List<OrderDetail> orderDetailList = orderDetailService.selectTableOrder(1);
        for (OrderDetail detail : orderDetailList) {
            Double price = detail.getPrice();
            Integer num = detail.getFoodCount();
            pay = price * num + pay;
            System.out.println(pay);
        }
        System.out.println(pay);
    }

}
