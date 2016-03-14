package com.deadpineapple.front.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by saziri on 10/03/2016.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    @RequestMapping(method= RequestMethod.GET)
    public String index(){
        return "index";
    }

}
