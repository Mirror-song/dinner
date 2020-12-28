package com.hqyj.dinner.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.dinner.entity.OrderDetail;
import com.hqyj.dinner.entity.Tables;
import com.hqyj.dinner.service.OrderDetailService;
import com.hqyj.dinner.service.TablesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Luo
 * @since 2020-10-19
 */
@Controller
@RequestMapping("orderDetail")
public class OrderDetailController {
    @Resource
    OrderDetailService orderDetailService;

    @Resource
    private TablesService tablesService;

    @RequestMapping("/orderTable")
    public String orderTable() {
        return "order_manage/orderTable";
    }

    @RequestMapping("orderPage")
    public String orderPage() {
        return "order_manage/order_display";
    }

    //预定一个餐桌
    @RequestMapping("/order")
    @ResponseBody
    public HashMap<String, Object> order(Tables tables) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Tables> queryWrapper = new QueryWrapper<>();
        tables.setStatus(0);
        queryWrapper.eq("id", tables.getId());
        if (tablesService.update(tables, queryWrapper)) {
            map.put("info", "预定成功");
        } else {
            map.put("info", "预定失败");
        }
        return map;
    }

    //翻台
    @RequestMapping("/cancel")
    @ResponseBody
    public HashMap<String, Object> cancel(Tables tables) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Tables> queryWrapper = new QueryWrapper<>();
        tables.setStatus(1);
        queryWrapper.eq("id", tables.getId());
        if (tablesService.update(tables, queryWrapper)) {
            map.put("info", "翻台成功");
        } else {
            map.put("info", "翻台失败");
        }
        return map;
    }

    //订餐按钮
    @RequestMapping("/orderDinner")
    @ResponseBody
    public Map<String, Integer> orderDinner(Tables tables) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("tableId", tables.getId());
        map.put("tableStatus", tables.getStatus());
        return map;
    }

    //去到点菜页面
    @RequestMapping("/menu/{tableId}&{tableStatus}")
    public ModelAndView menu(@PathVariable("tableId") Integer tableId, @PathVariable("tableStatus") Integer tableStatus, ModelAndView mv) {
        Tables tables = new Tables();
        tables.setId(tableId);
        tables.setStatus(tableStatus);
        System.out.println(tables + "****************");
/*        HashMap<String,Object> map = new HashMap<>();
        map.put("tables",tables);*/
        mv.addObject("tables", tables);
        mv.setViewName("order_manage/menu");
        return mv;
    }

    //添加一个菜
    @RequestMapping("/addCar")
    @ResponseBody
    public HashMap<String, Object> addCar(OrderDetail orderDetail) {
        HashMap<String, Object> map = new HashMap<>();

        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("table_id", orderDetail.getTableId());
        queryWrapper.eq("food_id", orderDetail.getFoodId());
        queryWrapper.eq("status", 0);
        if (orderDetailService.getOne(queryWrapper) != null) {
            UpdateWrapper<OrderDetail> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("food_count", orderDetail.getFoodCount());
            boolean update = orderDetailService.update(orderDetail, queryWrapper);
            if (update) {
                map.put("info", "加入成功");
            } else {
                map.put("info", "加入失败");
            }
            return map;
        }
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderNo = dateFormat.format(date);
        System.out.println(orderDetail.getTableId() + "++++++++++++++++++++++++++++++++++++");
        orderDetail.setStatus(0);//0是没下单
        orderDetail.setPay(0);//0没付钱
        orderDetail.setOrderNo(orderNo);

        int i = orderDetailService.add(orderDetail);
        if (i > 0) {
            map.put("info", "加入成功");
        } else {
            map.put("info", "加入失败");
        }
        return map;
    }

    //取消加入的菜
    @RequestMapping("/cancel2")
    @ResponseBody
    public HashMap<String, Object> cancel(OrderDetail orderDetail) {
        HashMap<String, Object> map = new HashMap<>();
        int result = orderDetailService.delete(orderDetail);
        if (result > 0) {
            map.put("info", "取消成功");
        } else {
            map.put("info", "取消失败");
        }
        return map;
    }

    //下单
    @RequestMapping("/confirm")
    @ResponseBody
    public HashMap<String, Object> confirm(OrderDetail orderDetail) {
        HashMap<String, Object> map = new HashMap<>();
        List<OrderDetail> orderDetailList = orderDetailService.select(orderDetail);
        UpdateWrapper<OrderDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", 1);
        if (orderDetailList != null) {
            for (OrderDetail orderDetail2 : orderDetailList) {
                orderDetail2.setStatus(1);
                orderDetailService.update(updateWrapper);
            }
            map.put("data", orderDetailList);
            map.put("info", "下单成功");
            return map;
        }
        map.put("info", "下单失败！如有疑问请询问服务员！");
        return map;

    }

    //详情及结算
    @RequestMapping("/search/{tableId}")
    public ModelAndView eachTableOrder(@PathVariable("tableId") Integer tableId, ModelAndView modelAndView) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setTableId(tableId);
        modelAndView.addObject("orderDetail", orderDetail);
        modelAndView.setViewName("order_manage/eachTableOrder");
        return modelAndView;
    }

    //查看点的菜
    @RequestMapping("/findOrder")
    @ResponseBody
    public HashMap<String, Object> findOrder(OrderDetail orderDetail) {
        HashMap<String, Object> map = new HashMap<>();
        List<OrderDetail> orderDetailList = orderDetailService.selectTableOrder(orderDetail.getTableId());
        map.put("data", orderDetailList);
        return map;
    }

    //计算价格
    @RequestMapping("/count")
    @ResponseBody
    public HashMap<String, Object> count(OrderDetail orderDetail) {
        HashMap<String, Object> map = new HashMap<>();
        double pay = 0.0;
//        double pay2 = 0.0;

        List<OrderDetail> orderDetailList = orderDetailService.selectTableOrder(orderDetail.getTableId());
        for (OrderDetail detail : orderDetailList) {
            Double price = detail.getPrice();
            Integer num = detail.getFoodCount();
            pay = price * num + pay;
            //System.out.println(pay);
        }
        /*for (OrderDetail detail : orderDetailList) {
            Double price = detail.getVipPrice();
            Integer num = detail.getFoodCount();
            pay2 = price * num + pay2;
            //System.out.println(pay);
        }*/
        map.put("data", pay);
//        map.put("data2", pay2);
        return map;
    }

    //付钱
    @RequestMapping("/pay")
    @ResponseBody
    public HashMap<String, Object> pay(OrderDetail orderDetail) {
        HashMap<String, Object> map = new HashMap<>();
        UpdateWrapper<OrderDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("pay", 1);
        //找到对应餐桌且上了菜的
        List<OrderDetail> orderDetailList = orderDetailService.selectNotPay(orderDetail.getTableId());
        if (orderDetailList.size() != 0) {
            for (OrderDetail detail : orderDetailList) {

                orderDetailService.update(detail);
            }
            map.put("data", "支付成功");

            return map;
        }
        map.put("data", "订单尚未完成");
        return map;
    }

    //商家出菜页面
    @RequestMapping("/chucai")
    public String chucai() {
        return "order_manage/chucai";
    }

    @RequestMapping("/cai")
    @ResponseBody
    public HashMap<String, Object> cai(OrderDetail orderDetail) {
        HashMap<String, Object> map = new HashMap<>();
        List<OrderDetail> orderDetailList = orderDetailService.selectNotMake(orderDetail);
        map.put("data", orderDetailList);
        return map;
    }

    @RequestMapping("/make")
    @ResponseBody
    public HashMap<String, Object> make(OrderDetail orderDetail) {
        HashMap<String, Object> map = new HashMap<>();
        //找出数据
        List<OrderDetail> one = orderDetailService.selectOntMakeFood(orderDetail);
        if (one.size() >= 1) {
            //修改数据
            int update = orderDetailService.update2(orderDetail);
            if (update > 0) {
                map.put("info", "成功");
                return map;
            }
            map.put("info", "操作失败！");
            return map;
        }
        map.put("info", "操作失败！");
        return map;
    }

    @ResponseBody
    @RequestMapping("findAllOrder")
    public HashMap<String, Object> findAllOrder(OrderDetail order) {
        HashMap<String, Object> map = new HashMap<>();
        PageHelper.startPage(order.getPage(), order.getRows());
        List<OrderDetail> orderDetails = orderDetailService.findAll();
        PageInfo<OrderDetail> pageInfo = new PageInfo<>(orderDetails);
        map.put("total", pageInfo.getTotal());
        map.put("pages", pageInfo.getPages());
        map.put("endPage", pageInfo.getNavigateLastPage());
        map.put("curPage", order.getPage());
        map.put("data", pageInfo.getList());
        return map;
    }

    @ResponseBody
    @RequestMapping("del")
    public HashMap<String, Object> del(OrderDetail order) {
        HashMap<String, Object> map = new HashMap<>();
        final boolean result = orderDetailService.removeById(order.getId());
        if (result) {
            map.put("info", "删除成功");
        } else {
            map.put("info", "删除失败");
        }
        return map;
    }
}
