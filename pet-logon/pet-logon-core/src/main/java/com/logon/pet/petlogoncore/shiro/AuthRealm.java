package com.logon.pet.petlogoncore.shiro;

import com.logon.pet.petlogoncore.Repository.UserRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import com.logon.pet.petlogoncore.domain.Account;

public class AuthRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(AuthRealm.class);

    /*** 用户业务处理类,用来查询数据库中用户相关信息 ***/
    @Autowired
    UserRepository userRepository;

    /***
     * 获取用户授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        // 获取用户名
        String loginName = (String) principalCollection.fromRealm(getName()).iterator().next();
        // 判断用户名是否存在
        if (StringUtils.isEmpty(loginName)) {
            return null;
        }
        // 查询登录用户信息
        Account account = userRepository.findByUserId(loginName);
        if (account == null) {
            logger.warn("用户[" + loginName + "]信息不存在");
            return null;
        }
        // 创建一个授权对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 进行权限设置
//        List<String> permissions = account.getPermissions();
//        if (permissions != null && !permissions.isEmpty()) {
//            info.addStringPermissions(permissions);
//        }
        // 角色设置
//        List<String> roles = account.getRoles();
//        if (roles != null) {
//            info.addRoles(roles);
//        }

        return info;
    }

    /**
     * 获取用户认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        logger.info("##################执行Shiro登陆认证##################");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 通过表单接收的用户名
        String loginName = token.getUsername();
        if (loginName != null && !"".equals(loginName)) {
            // 模拟数据库查询用户信息
            Account account = userRepository.findByUserId(loginName);

            if (account != null) {
                // 登陆的主要信息: 可以是一个实体类的对象, 但该实体类的对象一定是根据 token 的 username 查询得到的.
//                Object principal = token.getPrincipal();
                // 创建shiro的用户认证对象
                // 注意该对象的密码将会传递至后续步骤与前面登陆的subject的密码进行比对。
                SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(account,
                        account.getAccountPassword(), getName());

                return authenticationInfo;
            }
        }
        return null;
    }
}