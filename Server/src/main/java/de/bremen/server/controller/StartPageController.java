package de.bremen.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.bremen.server.utils.ServerUrlsPath.HOME;

@RestController
public class StartPageController {

    @RequestMapping(HOME)
    public String startPage(){
        return "Spacestudio Server is running...";
    }

}
