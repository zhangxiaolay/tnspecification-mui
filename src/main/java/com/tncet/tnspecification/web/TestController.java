package com.tncet.tnspecification.web;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tncet.tnspecification.service.TestService;
import com.tncet.tnspecification.std.StdResult;
import com.tncet.tnspecification.std.StdStatus;



@RestController
public class TestController {
    @Resource
    private TestService testService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public StdResult<Boolean> test() {
        Boolean res = testService.test();
        return new StdResult<>(StdStatus.STATUS_200, res);
    }   
}
