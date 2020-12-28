package com.hqyj.dinner.shrio;

import com.hqyj.dinner.entity.User;
import com.hqyj.dinner.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Program: dinner
 * Description: Realm授权、认证
 * Author: Luo
 * Date: 2020-10-20 00:20
 */
public class MyAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        String userName = (String) subject.getPrincipal();

        //根据用户名查询用户拥有的权限或者角色
        List<String> list = userService.selectRole(userName);
        //创建shiro授权对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //给用户赋予角色
        simpleAuthorizationInfo.addRoles(list);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取登录用户的凭证
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //获取用户名
        String userName = (String) token.getPrincipal();
        //查询数据库,获取用户对象
        User user_db = userService.selectUserByUserName(userName);
        //判断用户是否存在
        if (user_db != null) {
            //创建用户凭证 ，这个对象会把数据库查询出来的用户信息和登录的用户信息进行比对
            return new SimpleAuthenticationInfo(userName, user_db.getPwd(), new Md5Hash(userName), this.getName());
        }
        return null;
    }
}
