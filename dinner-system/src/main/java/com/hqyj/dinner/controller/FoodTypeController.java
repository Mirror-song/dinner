package com.hqyj.dinner.controller;


import com.hqyj.dinner.entity.FoodType;
import com.hqyj.dinner.service.FoodTypeService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Luo
 * @since 2020-10-19
 */
@RestController
@RequestMapping("foodType")
public class FoodTypeController {
    @Resource
    FoodTypeService foodTypeService;
    @RequestMapping("/findAll")
    public HashMap<String,Object> findAll(){
        List<FoodType> foodTypeList = foodTypeService.findAll();
        HashMap<String,Object> map = new HashMap<>();
        map.put("data",foodTypeList);
        return map;
    }
    @RequestMapping("/add")
    public HashMap<String,Object> add(FoodType foodType) {
        HashMap<String,Object> map = new HashMap<>();
        int result = foodTypeService.add(foodType);
        if(result>0) {
          map.put("info","新增成功");
          return map;
        }else {
            map.put("info","新增失败");
            return map;
        }
    }
    @RequestMapping("/update")
    public HashMap<String,Object> update(FoodType foodType) {
        HashMap<String, Object> map = new HashMap<>();
        int result = foodTypeService.update(foodType);
        if(result>0) {
            map.put("info","修改成功");
            return map;
        }else {
            map.put("info","修改失败");
            return map;
        }
    }
    @RequestMapping("/del")
    public HashMap<String,Object> del(FoodType foodType) {
        HashMap<String, Object> map = new HashMap<>();
        int result = foodTypeService.delete(foodType);
        if(result>0) {
            map.put("info","删除成功");
            return map;
        }else {
            map.put("info","删除失败");
            return map;
        }
    }
    @RequestMapping("/select")
    public HashMap<String,Object> select(FoodType foodType){
        HashMap<String, Object> map = new HashMap<>();
        List<FoodType> foodTypeList = foodTypeService.select(foodType);
        map.put("data",foodTypeList);
        return map;
    }

}
