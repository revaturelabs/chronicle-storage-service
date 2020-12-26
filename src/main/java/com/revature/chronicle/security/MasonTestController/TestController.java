package com.revature.chronicle.security.MasonTestController;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mason-test")
public class TestController {

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String testMason() {
        return "User is logged in";
    }

}
