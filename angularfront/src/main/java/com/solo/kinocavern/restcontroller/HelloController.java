package com.solo.kinocavern.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String abc(){
        return "WORKING!";
    }
}
