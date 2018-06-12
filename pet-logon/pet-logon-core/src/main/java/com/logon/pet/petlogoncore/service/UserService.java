package com.logon.pet.petlogoncore.service;

import com.logon.pet.petlogoncore.domain.Account;
import com.logon.pet.petlogoncore.domain.User;

public interface UserService {

    User getUserBySi(Long id);

    User getUserBySiTest(Long id);

    Account getAccountByAccountId(String userId);
}
