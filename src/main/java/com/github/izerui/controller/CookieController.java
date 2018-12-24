package com.github.izerui.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CookieController {

    @PostMapping("/assistant")
    public Map getCookie(@RequestBody Map<String, String> domainCookies) {
        System.out.println(domainCookies);
        return new HashMap() {{
            put("status", "1");
            put("successful", true);
        }};
    }
}
