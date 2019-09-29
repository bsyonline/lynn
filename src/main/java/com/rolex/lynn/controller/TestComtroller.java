package com.rolex.lynn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rolex
 * @Since 29/09/2019
 */
//@RestController
public class TestComtroller {
    
//    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
