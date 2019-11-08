package com.logdb.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ErrorMappingController implements ErrorController {

    protected static final String PATH = "/error";
    protected static final String FORBIDDEN = "/403";

    @RequestMapping(PATH)
    public ModelAndView error() {
        return new ModelAndView(PATH);
    }

    @GetMapping("/403")
    public ModelAndView error403() {
        return new ModelAndView(FORBIDDEN);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
