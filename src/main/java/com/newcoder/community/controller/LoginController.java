package com.newcoder.community.controller;

import com.newcoder.community.entity.User;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class LoginController implements CommunityConstant {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage(){
        return "/site/register";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage(){
        return "/site/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, User user){
        Map<String, Object> map = userService.register(user);
        if(map == null || map.isEmpty()){
            model.addAttribute("msg", "注册成功， 我们已经向您的邮箱发送了一封激活邮件， 请尽快激活！");
            model.addAttribute("targer", "/index");
            return "/site/operate-result";
        }else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }

    }
    // activation/101/code
    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userid, @PathVariable("code") String code){
        int activation = userService.activation(userid, code);
        if(activation == ACTIVATION_SUCCESS){
            model.addAttribute("msg", "激活成功, 您的账号已经可以正常使用");
            model.addAttribute("targer", "/login");
        }else if(activation == ACTIVATION_REPEAT){
            model.addAttribute("msg", "无效操作, 改账号已经激活过了");
            model.addAttribute("targer", "/index");
        }else {
            model.addAttribute("msg", "激活失败,您提供的激活码不正确");
            model.addAttribute("targer", "/index");
        }
        return "/site/operate-result";

    }

}
