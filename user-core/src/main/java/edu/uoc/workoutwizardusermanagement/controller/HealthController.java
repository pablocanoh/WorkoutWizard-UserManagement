package edu.uoc.workoutwizardusermanagement.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class HealthController {


    @RequestMapping("/health")
    public String health() {
        return "OK";
    }
}
