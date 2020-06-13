package de.spaceStudio.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartPageController {

    @RequestMapping("/")
    public String startPage(){
        return "Spacestudio Server is running...";
    }

}
