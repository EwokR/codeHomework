package com.example.codehomework.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("info")
@RestController
@Profile("admin")
public class InfoController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/port")
    public int getPort() {
        return port;
    }
}
