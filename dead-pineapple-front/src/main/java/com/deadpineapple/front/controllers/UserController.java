package com.deadpineapple.front.controllers;

import com.deadpineapple.dal.dao.IUserDao;
import com.deadpineapple.dal.dao.UserDao;
import com.deadpineapple.dal.entity.UserAccount;
import com.deadpineapple.front.Forms.LoginForm;
import com.deadpineapple.front.HibernateUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.hibernate.*;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

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
    public ModelAndView addUser(Model model, LoginForm loginForm){
        System.out.println("Invoking User");
        model.addAttribute("loginAttribute", loginForm);
        return new ModelAndView("userForm", "userAccount", new UserAccount());
    }
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String saveUser(@ModelAttribute("user")UserAccount user,
                           BindingResult result, ModelMap model){
        userBdd.saveUser(user);
        return "index";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(Model model, LoginForm loginform, Locale locale, HttpServletRequest request) throws Exception {

        String username = loginform.getUsername();
        String password = loginform.getPassword();

        if(username != null && password != null){

            if( userBdd.checkCredentials(username, password) != null ){
                //return "welcome";
                request.getSession().setAttribute("LOGGEDIN_USER", loginform);
                return "redirect:/upload";
            }else{
                return "redirect:/index.failed";
            }
        }else{
            return "redirect:/index.failed";
        }
    }
    @RequestMapping(value="/logOff", method = RequestMethod.GET)
    public String logOff(Model model, LoginForm loginform, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("LOGGEDIN_USER");
        model.addAttribute("loginAttribute", loginform);
        return "index";
    }

}
