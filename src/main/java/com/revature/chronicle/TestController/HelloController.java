package com.revature.chronicle.TestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHello() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

}