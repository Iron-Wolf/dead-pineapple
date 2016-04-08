package com.deadpineapple.front.controllers;

import com.deadpineapple.dal.dao.IUserDao;
import com.deadpineapple.dal.dao.UserDao;
import com.deadpineapple.dal.entity.UserAccount;
import com.deadpineapple.front.HibernateUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.hibernate.*;

import javax.ejb.EJB;

/**
 * Created by saziri on 10/03/2016.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserDao userBdd;
    public void setUserDAO(IUserDao userDAO) {
        this.userBdd = userDAO;
    }
    @RequestMapping(value="/add", method= RequestMethod.GET)
    public ModelAndView addUser(){
        System.out.println("Invoking User");
        return new ModelAndView("userForm", "userAccount", new UserAccount());
    }
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String saveUser(@ModelAttribute("user")UserAccount user,
                           BindingResult result, ModelMap model){
        userBdd.saveUser(user);
        return "index";
    }
    @RequestMapping(value="/add", method=RequestMethod.POST, params={"age = 60", "notExpert"})
    public String saveOldUser(){
        System.out.println("Save Old User ahahah x)");
        return "index";
    }
}
