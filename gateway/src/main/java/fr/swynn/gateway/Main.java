package fr.swynn.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Main {

    @GetMapping("/test")
    public String test() {
        return "Hello World!";
    }
}
