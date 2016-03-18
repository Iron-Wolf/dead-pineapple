package com.deadpineapple.front.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by saziri on 10/03/2016.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value="/add", method= RequestMethod.GET)
    public String addUser(){
        System.out.println("Invoking User");
        return "userForm";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String saveUser(Model model){
        System.out.println("Save User");
        return "test";
    }
    @RequestMapping(value="/add", method=RequestMethod.POST, params={"age = 60", "notExpert"})
    public String saveOldUser(){
        System.out.println("Save Old User ahahah x)");
        return "index";
    }
}
