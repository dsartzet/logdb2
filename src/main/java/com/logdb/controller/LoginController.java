package com.logdb.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    protected static final String LOGIN = "/login";
    protected static final String REDIRECT_HOME = "redirect:/home";

    @GetMapping(LOGIN)
    public String login(Principal principal) {

        if (principal != null) {
            return REDIRECT_HOME;
        }
        return LOGIN;
    }

}
