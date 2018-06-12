package com.logon.pet.petlogonweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.logon.pet.petlogoncore.domain.Account;
import com.logon.pet.petlogoncore.util.UserUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping(value = "/index")
@EnableAutoConfiguration
public class IndexController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        JSONObject result = new JSONObject();
        String hostIp = "";
        Account account = new Account();
        try {
            hostIp = InetAddress.getLocalHost().getHostAddress();
            account = UserUtils.getCurrentUser();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        result.put("ip", hostIp);
        result.put("accountId", account.getAccountId());
        result.put("accountName", account.getAccountName());
        return result.toString();
    }
}
