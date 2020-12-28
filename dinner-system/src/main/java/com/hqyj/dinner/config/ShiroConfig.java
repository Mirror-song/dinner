package com.hqyj.dinner.config;

import java.util.LinkedHashMap;
import java.util.Map;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.hqyj.dinner.shrio.MyAuthorizingRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * Program: dinner
 * Description: Shiro配置
 * Author: Luo
 * Date: 2020-10-20 01:07
 */

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
       /* 配置过滤器：
            1.认证过滤器
                anon：无需认证
                authc：必须认证
                authcBasic：需要通过 HTTPBasic 认证
                user：不一定通过认证，只要曾经被 Shiro 记录即可，比如：记住我
            2.授权过滤器
                perms：必须拥有某个权限才能访问
                roles：必须拥有某个角色才能访问
                port：请求的端口必须是指定值才可以
                rest：请求必须基于 RESTful，POST、PUT、GET、DELETE
                ssl：必须是安全的 URL 请求，协议 HTTPS
        */
        // 存放自定义的filter
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("roles", new ShiroRoleFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        //配置角色认证
        Map<String, String> map = new LinkedHashMap<>();
        //放行静态资源文件
        map.put("/static/**", "anon");
        //需认证
        map.put("/index", "authc");
        map.put("/food/foodPage", "roles[店长]");
        map.put("/food/foodType", "roles[店长]");
        map.put("/table/tablePage", "roles[店长]");
        map.put("/orderDetail/del", "roles[店长]");
        map.put("/orderDetail/orderPage", "roles[店长,服务员,后厨]");
//        map.put("/**", "authc");

        // 设置拦截后跳转到login.html页面，若不设置，默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("myAuthorizingRealm") MyAuthorizingRealm myAuthorizingRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 设置 myAuthorizingRealm
        defaultWebSecurityManager.setRealm(myAuthorizingRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public MyAuthorizingRealm myAuthorizingRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        final MyAuthorizingRealm myAuthorizingRealm = new MyAuthorizingRealm();
        //注入密码匹配器
        myAuthorizingRealm.setCredentialsMatcher(matcher);
        return myAuthorizingRealm;
    }

    //配置密码匹配器
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //配置加密方式为MD5
        matcher.setHashAlgorithmName("MD5");
        //配置加密次数为1024
        matcher.setHashIterations(1024);
        // true 密码加密用hex编码; false 用base64编码
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    //在thymeleaf中使用shiro标签
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
