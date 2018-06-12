package com.logon.pet.petlogonweb.controller;


import com.logon.pet.petlogoncore.redis.RedisCacheSettings;
import com.logon.pet.petlogoncore.constants.AppProps;
import com.logon.pet.petlogoncore.domain.Account;
import com.logon.pet.petlogoncore.domain.User;
import com.logon.pet.petlogoncore.enums.CommonDeleteFlag;
import com.logon.pet.petlogoncore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


public class TestController {

    @Autowired
    UserService userService;

    @Autowired
    private AppProps appProps;

    @Autowired
    private RedisCacheSettings redisCacheProps;

    @RequestMapping(value = "/detail/{id}")
    public ModelAndView hello(@PathVariable("id") String id) {
        Long userSi = Long.parseLong(id);
        User user = userService.getUserBySi(userSi);
        ModelAndView view = new ModelAndView("index/index");
        view.addObject("userName", "Tom");
        return view;
    }

    @RequestMapping(value = "/test/{id}")
    public ModelAndView hello2(@PathVariable("id") String id) {
        Long userSi = Long.parseLong(id);
        User user = userService.getUserBySiTest(userSi);

        Account account = new Account();


        CommonDeleteFlag.valueOf("");

        ModelAndView view = new ModelAndView("index/index");
        view.addObject("userName", "Tom");
        return view;
    }
    public static void main(String[] args) {
        System.out.println(CommonDeleteFlag.DELETE.getName());
        System.out.println(CommonDeleteFlag.DELETE.getValue());
    }
}
