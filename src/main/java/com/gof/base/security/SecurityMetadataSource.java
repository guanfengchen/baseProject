package com.gof.base.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author gfchen
 * @since 1.0
 */
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final static Logger LOG = LoggerFactory.getLogger(SecurityMetadataSource.class);
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    private RequestMatcher pathMatcher = null;

    public SecurityMetadataSource(){
        LOG.info("====SecurityMetadataSource=====init resources and role");
        loadResourceDefine();
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        LOG.info("requestUrl is " + request.getRequestURL());
        if(resourceMap == null) {
            loadResourceDefine();
        }
        Iterator<String> it = resourceMap.keySet().iterator();

        while (it.hasNext()) {
            String resURL = it.next();
            pathMatcher = new AntPathRequestMatcher(resURL);
            if (pathMatcher.matches(request)) {
                Collection<ConfigAttribute> returnCollection = resourceMap.get(resURL);
                return returnCollection;
            }
        }

        return null;
    }

    //加载所有资源与权限的关系
    private void loadResourceDefine() {
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
        ConfigAttribute ca = new SecurityConfig("ROLE_USER");
        atts.add(ca);
        resourceMap.put("/aaa.htm", atts);

        Collection<ConfigAttribute> atts2 = new ArrayList<ConfigAttribute>();
        ConfigAttribute ca2 = new SecurityConfig("ROLE_ADMIN");
        atts2.add(ca2);
        resourceMap.put("/aaa/**", atts2);

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
