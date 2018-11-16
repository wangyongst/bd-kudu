package com.myweb.controller;

import com.myweb.service.OneService;
import com.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin("*")
@Controller
public class OneController {

    @Autowired
    public OneService oneService;

    @ResponseBody
    @RequestMapping(value = "/send/zengxian", method = RequestMethod.POST)
    public Result sendZengxian() {
        return oneService.query();
    }
}
