package com.personal;

import com.personal.utils.SumTwoValue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class WebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }

    @GetMapping("/hello")
    public double hello() {
        return SumTwoValue.sum(1.0, 2.0);
    }
}
