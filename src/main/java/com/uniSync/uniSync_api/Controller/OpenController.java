package com.uniSync.uniSync_api.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenController {

    @GetMapping("/open-test")
    public String openTest() {
        return "Hello from Open Test!";
    }
} 