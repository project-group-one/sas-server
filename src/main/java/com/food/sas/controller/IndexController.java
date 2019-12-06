package com.food.sas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author dong_gui on 2019/11/24.
 */
@Controller
public class IndexController {

    @GetMapping(value = "/admin")
    public String admin() {
        return "admin/index";
    }

    @GetMapping(value = "/web")
    public String web() {
        return "web/index";
    }
}
