package com.gof.base.mvc.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * @author gfchen
 * @since 1.0
 */

@Controller
public class AdminController {

    @RequestMapping(value="admin/createUser.htm", method = RequestMethod.GET)
    public String createUser() {
        return "admin/createUser";
    }

    @RequestMapping(value="admin/primefacesIndex.htm", method = RequestMethod.GET)
    public String primefacesIndex() throws IOException {
        return "admin/primefacesIndex";
    }
}
