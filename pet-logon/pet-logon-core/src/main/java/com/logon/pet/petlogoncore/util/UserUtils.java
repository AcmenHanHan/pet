package com.logon.pet.petlogoncore.util;

import com.logon.pet.petlogoncore.domain.Account;
import org.apache.shiro.SecurityUtils;

public class UserUtils {

    public static Account getCurrentUser() {
        return (Account) SecurityUtils.getSubject().getPrincipal();
    }
}
