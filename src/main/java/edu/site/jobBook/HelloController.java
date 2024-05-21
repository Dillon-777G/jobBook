package edu.site.jobBook;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {  

    @GetMapping("/")
    public String Hello(){
        return "Hello world!";
    }
    
}
