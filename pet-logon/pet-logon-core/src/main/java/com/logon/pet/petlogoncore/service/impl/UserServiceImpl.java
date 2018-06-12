package com.logon.pet.petlogoncore.service.impl;

import com.logon.pet.petlogoncore.Repository.UserRepository;
import com.logon.pet.petlogoncore.domain.Account;
import com.logon.pet.petlogoncore.domain.User;
import com.logon.pet.petlogoncore.service.UserService;
import com.logon.pet.petlogoncore.service.abstractservice.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    @Cacheable(value = "account", key = "#userId", cacheManager = "redisCacheManager")
    public Account getAccountByAccountId(String userId) {
        Account user = userRepository.findByUserId(userId);
        return user;
    }






    @Override
    public User getUserBySi(Long id) {
        return null;
    }

    @Override
    public User getUserBySiTest(Long id) {
        return null;
    }
}