package com.taotao.rest.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestTaotaoRestServiceController {

    @RequestMapping(value = "/httpclient/post", method = RequestMethod.POST)
    @ResponseBody
    public String testPost(String username, String password) {
        return "username: " + username + "| password: " + password;
    }
}
