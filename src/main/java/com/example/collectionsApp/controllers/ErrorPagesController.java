package com.example.collectionsApp.controllers;

import com.example.collectionsApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
public class ErrorPagesController {

    @Autowired
    private UserService userService;

    @RequestMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(Model model) {
        model.addAttribute("isAuthentication", userService.isAuthentication());
        model.addAttribute("authenticationName", userService.getAuthenticationName());
        System.out.println("404");
        return "/error/404";
    }

    @RequestMapping("/403")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String forbidden(Model model) {
        model.addAttribute("isAuthentication", userService.isAuthentication());
        model.addAttribute("authenticationName", userService.getAuthenticationName());
        return "/error/403";
    }

    @RequestMapping("/500")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerError(Model model) {
        model.addAttribute("isAuthentication", userService.isAuthentication());
        model.addAttribute("authenticationName", userService.getAuthenticationName());
        return "/error/500";
    }
}
