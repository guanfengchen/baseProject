package com.gof.base.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author gfchen
 * @since 1.0
 */
public class BaseAccessDecisionManager implements AccessDecisionManager {

    private final static Logger LOG = LoggerFactory.getLogger(BaseAccessDecisionManager.class);
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        LOG.info("======check reocures and user's role" );
        if(configAttributes == null) {
            return;
        }
        //所请求的资源拥有的权限(一个资源对多个权限)
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while(iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            //访问所请求资源所需要的权限
            String needPermission = configAttribute.getAttribute();
            System.out.println("needPermission is " + needPermission);
            //用户所拥有的权限authentication
            for(GrantedAuthority ga : authentication.getAuthorities()) {
                if(needPermission.equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        //没有权限
        throw new AccessDeniedException("NO Permission ");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
