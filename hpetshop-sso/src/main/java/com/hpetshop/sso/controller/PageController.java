package com.hpetshop.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用于页面之间的跳转
 *
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.sso.controller
 * @Author: wushaochuan
 * @CreateDate: 2018/4/25 22:11
 * @UpdateUser:
 * @UpdateDate: 2018/4/25 22:11
 * @Version: 1.0
 **/
@RequestMapping("/page")
@Controller
public class PageController {
    /**
     * 用于跳转到注册界面
     *
     * @return java.lang.String
     * @author wushaochuan
     * @date 2018/4/25 22:19
     */
    @RequestMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    /**
     * 用于跳转到登录页面
     * redirect 回调url
     *
     * @return java.lang.String
     * @author wushaochuan
     * @date 2018/4/25 22:37
     */
    @RequestMapping("/login")
    public String showLoginPage(String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }
}
