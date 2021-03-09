package cn.lxj.demo.service;

import cn.lxj.db.annotation.TargetDataSource;
import cn.lxj.demo.dao.TestMapper;
import cn.lxj.demo.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    TestMapper testMapper;
    @Autowired
    Test1Service test1Service;
    @Autowired
    Test2Service test2Service;

//    @TargetDataSource("test")
    public Integer insert() {
        Order order = new Order();
        order.setUserId(2);
        order.setName("test");
        return testMapper.insert(order);
    }

    public void insertMulti() {
        test1Service.insert();
        test2Service.insert();
    }
}
