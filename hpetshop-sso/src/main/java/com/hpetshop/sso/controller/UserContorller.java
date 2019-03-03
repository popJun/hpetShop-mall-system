package com.hpetshop.sso.controller;

import com.hpetshop.pojo.HpUser;
import com.hpetshop.sso.service.UserService;
import com.hpetshop.utils.CookieUtils;
import com.hpetshop.utils.ExceptionUtil;
import com.hpetshop.utils.Result;
import com.hpetshop.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于对用户判断的各种操作
 *
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.sso.controller
 * @Author: wushaochuan
 * @CreateDate: 2018/4/23 19:45
 * @UpdateUser:
 * @UpdateDate: 2018/4/23 19:45
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/user")
public class UserContorller {
    @Autowired
    private UserService userService;

    /**
     * 用于检查数据
     *
     * @param param, type, callback
     * @return java.lang.Object
     * @author wushaochuan
     * @date 2018/4/23 21:39
     */
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkUser(@PathVariable String param, @PathVariable Integer type, String callback) {
        Result result = null;
        String type1 = String.valueOf(type);
        if (StringUtils.isEmpty(param)) {
            result = Result.error(400, "校验内容不能为空");
        } else if (type == null) {
            result = Result.error(400, "类型不能为空");
        } else if (!(type1.equals("1") || type1.equals("2") || type1.equals("3"))) {
            result = Result.error(400, "校验内容错误");
        }
        //校验出错
        if (result != null) {
            if (callback != null) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            } else {
                return result;
            }
        }
        //调用服务
        try {
            result = userService.checkUser(param, type);
        } catch (Exception e) {
            result = Result.error(400, ExceptionUtil.getStackTrace(e));
        }
        //校验出错
        if (callback != null) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        } else {
            return result;
        }
    }

    /**
     * 注册用户
     *
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/23 21:35
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUser(HpUser hpUser) {
        try {
            Result result = userService.saveUser(hpUser);
            return result;
        } catch (Exception e) {
            return Result.error(400, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 用户登录
     *
     * @param username, password
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/23 22:33
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result getUser(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        try {
            Result result = userService.getUser(username, password, request, response);
            return result;
        } catch (Exception e) {
            return Result.error(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 通过token判断登录状态
     *
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/24 21:43
     */
    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        Result result = null;
        try {
            result = userService.getUserByToken(token);
        } catch (Exception e) {
            result = Result.error(500, ExceptionUtil.getStackTrace(e));
        }
        //是否为jsonp调用
        if (StringUtils.isEmpty(callback)) {
            return result;
        } else {
            //jsonp调用
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
    }

    @RequestMapping("/logout")
    public String logoutByToken(HttpServletRequest request, HttpServletResponse response, String callback) {
        String token = CookieUtils.getCookieValue(request, "HP_TOKEN");
        Result result = userService.logoutByToken(request, response, token);
        if (StringUtils.isEmpty(callback)) {
            return "redirect:/page/login.html";
        } else {
            //jsonp调用
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return "redirect:/page/login.html";
        }
    }
}

