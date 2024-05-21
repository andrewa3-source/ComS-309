package org.springframework.samples.netflix.system;


import org.springframework.web.bind.annotation.*;


@RestController
class WelcomeController {

    @GetMapping(value = "/")
    public String welcome() {
        return "Proceed to http://localhost:8080/create/initialize to create list of shows.";
    }
}
