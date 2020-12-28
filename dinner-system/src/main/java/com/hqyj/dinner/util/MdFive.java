package com.hqyj.dinner.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Component;

/**
 * md5 密码加密工具类
 */
@Component
public class MdFive {
    /**
     *
     * @param password 密码
     * @param saltValue 盐值
     * @return  md5加密后的密码
     */
	public String encrypt(String password,String saltValue){
	    //创建MD5算法对象
        Object salt = new Md5Hash(saltValue);
        //SimpleHash(算法名称,要加密的密码,盐值,加密次数)
        Object result = new SimpleHash("MD5", password, salt, 1024);
        return result.toString();
	}

}
