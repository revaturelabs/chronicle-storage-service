package com.revature.chronicle.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path="/file", method = {RequestMethod.GET, RequestMethod.POST})
public class FileUploadController {

    //@Autowired
//    private S3Service service;

    @PostMapping(path="/upload")
    @ResponseBody
    public void uploadFile( @RequestParam("name") String name, @RequestParam ("file") MultipartFile file){

    }
}
