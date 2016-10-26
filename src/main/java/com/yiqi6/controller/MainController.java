package com.yiqi6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 主页控制器
 *
 * @author dengjh
 * @create 2016-10-26 17:01
 **/
@Controller
public class MainController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
