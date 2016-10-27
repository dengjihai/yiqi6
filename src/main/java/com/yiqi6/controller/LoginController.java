package com.yiqi6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 登录
 *
 * @author dengjh
 * @create 2016-10-27 14:08
 **/
@Controller
public class LoginController {


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() {
        return "login";
    }



}
