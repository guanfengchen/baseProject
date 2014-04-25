package com.gof.base.jsf.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * @author gfchen
 * @since 1.0
 */

@ManagedBean(name="sampleBean")
@SessionScoped
public class SampleBean implements Serializable {
    private String name = "This editor is provided by PrimeFaces";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
