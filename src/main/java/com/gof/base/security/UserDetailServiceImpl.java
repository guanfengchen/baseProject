package com.gof.base.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author gfchen
 * @since 1.0
 */
public class UserDetailServiceImpl implements UserDetailsService {

    private final static Logger LOG = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.info("username is " + username);
        LOG.info("====load user information, create login successful user" );
        boolean enables = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        User userdetail = null;
        Set<GrantedAuthority> grantedAuths = new HashSet<GrantedAuthority>();
        if("haha".equals(username)){
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            userdetail = new User("haha", "haha", enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
        }else if("xixi".equals(username)){
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            userdetail = new User("xixi", "xixi", enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
        }

        return userdetail;
    }
}
