package com.yiqi6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主页控制器
 *
 * @author dengjh
 * @create 2016-10-26 17:01
 **/
@Controller
public class MainController {

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/chart")
    public String chart() {
        return "chart";
    }

    @RequestMapping(value = "/form-amazeui")
    public String form_amazeui() {
        return "form-amazeui";
    }

    @RequestMapping(value = "/form-line")
    public String form_line() {
        return "form-line";
    }

    @RequestMapping(value = "/form-news")
    public String form_news() {
        return "form-news";
    }

    @RequestMapping(value = "/form-news-list")
    public String form_news_list() {
        return "form-news-list";
    }

    @RequestMapping(value = "/table-font-list")
    public String table_font_list() {
        return "table-font-list";
    }

    @RequestMapping(value = "/table-images-list")
    public String table_images_list() {
        return "table-images-list";
    }

}
