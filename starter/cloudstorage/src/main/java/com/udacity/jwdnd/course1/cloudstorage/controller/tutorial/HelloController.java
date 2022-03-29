package com.udacity.jwdnd.course1.cloudstorage.controller.tutorial;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    private Map<String,Object> map = new HashMap<>();

    @RequestMapping("/hello")
//    @ResponseBody
    public  Map<String,Object> Hello(){
        map.put("name", "Samuel Olufemi");
        map.put("city", "Lagos");
        return  map;
    }
}
