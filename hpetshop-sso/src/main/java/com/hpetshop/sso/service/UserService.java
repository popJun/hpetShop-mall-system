package com.hpetshop.sso.service;

import com.hpetshop.pojo.HpUser;
import com.hpetshop.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于对用户判断的各种操作
 *
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.sso.service
 * @Author: wushaochuan
 * @CreateDate: 2018/4/23 19:30
 * @UpdateUser:
 * @UpdateDate: 2018/4/23 19:30
 * @Version: 1.0
 **/
public interface UserService {
    /**
     * 检查数据是否可用
     *
     * @param content , type
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/23 19:33
     */
    Result checkUser(String content, Integer type);

    /**
     * 用于注册
     *
     * @param hpUser 前台传来用户
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/23 21:32
     */
    Result saveUser(HpUser hpUser);

    /**
     * 用于查询用户信息
     *
     * @param username password
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/23 21:45
     */
    Result getUser(String username, String password, HttpServletRequest request, HttpServletResponse response);

    /**
     * 通过token判断登录状态
     *
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/24 21:43
     */
    Result getUserByToken(String token);

    /**
     * 用于安全退出
     *
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/25 18:01
     */
    Result logoutByToken(HttpServletRequest request, HttpServletResponse response, String token);
}
