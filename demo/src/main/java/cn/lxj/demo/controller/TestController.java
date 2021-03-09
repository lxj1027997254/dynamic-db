package cn.lxj.demo.controller;

import cn.lxj.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * Created by LXJ on 2021-03-09.
 */
@RestController
public class TestController {

    @Autowired
    DataSource dataSource;
    @Autowired
    TestService testService;

    @RequestMapping("ttt")
    public String testMulti() {
        testService.insertMulti();
        return "success";
    }
}
