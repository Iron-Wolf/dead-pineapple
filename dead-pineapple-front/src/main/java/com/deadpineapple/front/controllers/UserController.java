package com.deadpineapple.front.controllers;

import com.deadpineapple.dal.dao.IUserDao;
import com.deadpineapple.dal.entity.UserAccount;
import com.deadpineapple.front.Forms.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by saziri on 10/03/2016.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserDao userBdd;
    UserAccount user;
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
                           BindingResult result, ModelMap model, HttpServletRequest request) throws Exception {
        user.setPassword(getEncryptedPassword(user.getPassword()));
        Date creationDate = new Date();
        user.setCreationDate(creationDate);
        userBdd.saveUser(user);
        // Create a loginform and check in bdd if users exists
        LoginForm loginForm = new LoginForm();
        loginForm.setPassword(user.getPassword());
        loginForm.setUsername(user.getEmail());
        request.getSession().setAttribute("LOGGEDIN_USER", loginForm);
        return "redirect:/upload";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(LoginForm loginForm, HttpServletRequest request) throws Exception {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        if(username != null && password != null){
            user = userBdd.checkCredentials(username, getEncryptedPassword(password));
            if( user != null ){
                request.getSession().setAttribute("LOGGEDIN_USER", loginForm);
                request.getSession().setAttribute("USER_INFORMATIONS", user);
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
        session.removeAttribute("USER_INFORMATIONS");
        model.addAttribute("loginAttribute", loginform);
        return "index";
    }
    public static String getEncryptedPassword(String clearPassword)   {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(clearPassword.getBytes());
            return new sun.misc.BASE64Encoder().encode(md.digest());
        } catch (NoSuchAlgorithmException e) {
            //_log.error("Failed to encrypt password.", e);
        }
        return "";
    }

}
