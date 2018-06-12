package com.logon.pet.petlogoncore.config;


import com.logon.pet.petlogoncore.shiro.AuthRealm;
import com.logon.pet.petlogoncore.shiro.ShiroCacheManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public RedisSessionDAO redisSessionDAO() {
        ShiroCacheManager shiroCacheManager = new ShiroCacheManager(redisTemplate);
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(shiroCacheManager);
        return redisSessionDAO;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public RedisCacheManager shiroCacgeManager() {
        RedisCacheManager shiroRedisCacgeManager = new RedisCacheManager();
        ShiroCacheManager shiroCacheManager = new ShiroCacheManager(redisTemplate);
        shiroRedisCacgeManager.setRedisManager(shiroCacheManager);
        System.out.println(">>>>>>>>>>init shiroRedisCacgeManager");
        return shiroRedisCacgeManager;
    }

    @Bean
    public SessionManager sessionManager(RedisCacheManager redisCacheManager, RedisSessionDAO sessionDAO) {
        System.out.println(">>>>>redisCacheManager:" + redisCacheManager);
        System.out.println(">>>>>sessionDAO:" + sessionDAO);
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setGlobalSessionTimeout(600);
        sessionManager.setCacheManager(redisCacheManager);
        return sessionManager;
    }

    @Bean
    public AuthRealm shiroRealm(RedisCacheManager redisCacheManager) {
        System.out.println(">>>>>init shiroRealm redisCacheManager:" + redisCacheManager);
        AuthRealm realm = new AuthRealm();
        realm.setAuthenticationCachingEnabled(true);
        realm.setCachingEnabled(true);
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1);
        realm.setCredentialsMatcher(hashedCredentialsMatcher);
        // 根据情况使用缓存器
        realm.setCacheManager(redisCacheManager);//shiroEhCacheManager()
        return realm;
    }
    /***
     * 安全管理配置
     *
     * @return
     */
    @Bean
    public SecurityManager defaultWebSecurityManager(AuthRealm shiroRealm, RedisCacheManager redisCacheManager) {
        System.out.println(">>>>>>>>>>init defaultWebSecurityManager redisCacheManager:" + redisCacheManager);
        // DefaultSecurityManager defaultSecurityManager = new
        // DefaultSecurityManager();
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();// 注意:！！！初始化成这个将会报错java.lang.IllegalArgumentException:
        // SessionContext must be an HTTP compatible
        // implementation.：模块化本地测试shiro的一些总结
        // 配置
        securityManager.setRealm(shiroRealm);
        // 注意这里必须配置securityManager
        SecurityUtils.setSecurityManager(securityManager);
        // 根据情况选择缓存器
        securityManager.setCacheManager(redisCacheManager);//shiroEhCacheManager()

        return securityManager;
    }

    /**
     * 配置shiro的拦截器链工厂,默认会拦截所有请求，并且不可配置
     *
     * @return
     */
    @Bean
    @Lazy
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager defaultWebSecurityManager) {
        System.out.println(">>>>>init ShiroFilterFactoryBean defaultWebSecurityManager:" + defaultWebSecurityManager);
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        // 配置安全管理(必须)
        filterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        // 配置登陆的地址
        filterFactoryBean.setLoginUrl("/account/tologon");// 未登录时候跳转URL,如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        filterFactoryBean.setSuccessUrl("/account/logonsuccess");// 成功后欢迎页面
        filterFactoryBean.setUnauthorizedUrl("/403.do");// 未认证页面

        // 配置拦截地址和拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();// 必须使用LinkedHashMap,因为拦截有先后顺序
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问

        filterChainDefinitionMap.put("/userNoLogin.do*", "anon");// 未登录跳转页面不设权限认证
        filterChainDefinitionMap.put("/account/logon", "anon");// 登录接口不设置权限认真
        filterChainDefinitionMap.put("/static/**", "anon");// 登出不需要认证

        // 以下配置同样可以通过注解
        // @RequiresPermissions("user:edit")来配置访问权限和角色注解@RequiresRoles(value={"ROLE_USER"})方式定义
        // 权限配置示例,这里的配置理论来自数据库查询
        filterChainDefinitionMap.put("/user/**", "roles[ROLE_USER],perms[query]");// /user/下面的需要ROLE_USER角色或者query权限才能访问
        filterChainDefinitionMap.put("/admin/**", "perms[ROLE_ADMIN]");// /admin/下面的所有需要ROLE_ADMIN的角色才能访问

        // 剩下的其他资源地址全部需要用户认证后才能访问
        filterChainDefinitionMap.put("/**", "authc");
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        // 全部配置
        // anon org.apache.shiro.web.filter.authc.AnonymousFilter 匿名访问
        //
        // authc org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        // 需要登录,不需要权限和角色可访问
        //
        // authcBasic
        // org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
        //
        // perms
        // org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
        // 需要给定的权限值才能访问
        //
        // port org.apache.shiro.web.filter.authz.PortFilter
        //
        // rest org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
        //
        // roles org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
        // 需要给定的角色才能访问
        //
        // ssl org.apache.shiro.web.filter.authz.SslFilter
        //
        // user org.apache.shiro.web.filter.authc.UserFilter
        //
        // logout org.apache.shiro.web.filter.authc.LogoutFilter
        return filterFactoryBean;
    }
}
