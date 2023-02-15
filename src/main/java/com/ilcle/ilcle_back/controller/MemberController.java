package com.ilcle.ilcle_back.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class MemberController {

    @GetMapping("{id}")
    public String get(@PathVariable String id) {
        return id;
    }
}