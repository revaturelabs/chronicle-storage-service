package com.revature.chronicle.TestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String test() {
        return "User is logged in";
    }
    
    @GetMapping(path="hello", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHello() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

}
