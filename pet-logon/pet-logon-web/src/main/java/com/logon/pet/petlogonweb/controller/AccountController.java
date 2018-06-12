package com.logon.pet.petlogonweb.controller;

import com.logon.pet.petlogoncore.domain.Account;
import com.logon.pet.petlogoncore.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/account")
@EnableAutoConfiguration
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/tologon")
    public ModelAndView toLogon(){
        ModelAndView modelAndView = new ModelAndView("index/logon");
        modelAndView.addObject(new Account());
        return modelAndView;
    }

    @RequestMapping(value = "/logon")
    public ModelAndView logon(Account account){
        String loginName = account.getAccountId();
        try {
            // 创建shiro需要的token
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(account.getAccountId(), account.getAccountPassword().toCharArray());
//            usernamePasswordToken.setRememberMe(true);// 记住

            try {
                SecurityUtils.getSubject().login(usernamePasswordToken);
            } catch (UnknownAccountException uae) {
                logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,未知账户");
//                return "对用户[" + loginName + "]进行登录验证..验证未通过,未知账户";
            } catch (IncorrectCredentialsException ice) {
                logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,错误的凭证");
                ice.printStackTrace();
//                return "对用户[" + loginName + "]进行登录验证..验证未通过,错误的凭证";
            } catch (LockedAccountException lae) {
                logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,账户已锁定");
//                return "对用户[" + loginName + "]进行登录验证..验证未通过,账户已锁定";
            } catch (ExcessiveAttemptsException eae) {
                logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,错误次数过多");
//                return "对用户[" + loginName + "]进行登录验证..验证未通过,错误次数过多";
            } catch (AuthenticationException ae) {
                // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
                logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,堆栈轨迹如下");
                ae.printStackTrace();
//                return "用户名或密码不正确";
            }
//            return "Login Success!";
        } catch (Exception e) {
//            return "登陆时候发生异常," + e.getMessage();
        }

        ModelAndView modelAndView = new ModelAndView("index/index");
        return modelAndView;
    }

    @RequestMapping(value = "/logonsuccess")
    public ModelAndView logonsuccess(){
        Account account = (Account)SecurityUtils.getSubject().getPrincipal();
        System.out.println(account.getAccountName());
        ModelAndView modelAndView = new ModelAndView("index/logonsuccess");
        modelAndView.addObject(new Account());
        return modelAndView;
    }
}
