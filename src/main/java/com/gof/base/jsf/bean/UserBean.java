package com.gof.base.jsf.bean;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author gfchen
 * @since 1.0
 */
@ManagedBean(name="userBean")
@RequestScoped
public class UserBean {
    private String name;
    private String loginName;
    private String password;

    public void saveUser(){
        System.out.println("User saved===============");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
