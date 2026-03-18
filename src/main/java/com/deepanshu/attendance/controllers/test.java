package com.deepanshu.attendance.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    @GetMapping(path = "/api/v1/test")
    public String test(){
        return "working";
    }
}
