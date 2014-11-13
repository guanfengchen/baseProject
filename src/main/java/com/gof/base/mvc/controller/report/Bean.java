package com.gof.base.mvc.controller.report;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gfchen
 * @since 6.2
 */

public class Bean {

    public Bean(String name, String value){
        this.name = name;
        this.value = value;
    }

    public static List<Bean> mockBeanList(){
        List<Bean> result = new ArrayList<Bean>();
        Bean bean = new Bean("1系", "75");
        Bean bean2 = new Bean("2系", "150");
        Bean bean3 = new Bean("3系", "60");
        Bean bean4 = new Bean("4系", "112");
        result.add(bean);
        result.add(bean2);
        result.add(bean3);
        result.add(bean4);
        return result;
    }
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
