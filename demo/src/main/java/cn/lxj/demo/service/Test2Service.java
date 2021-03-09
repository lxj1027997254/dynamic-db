package cn.lxj.demo.service;

import cn.lxj.db.annotation.TargetDataSource;
import cn.lxj.demo.dao.TestMapper;
import cn.lxj.demo.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Test2Service {

    @Autowired
    TestMapper testMapper;

//    @TargetDataSource("test")
    public Integer insert() {
        Order order = new Order();
        order.setUserId(2);
        order.setName("test2");
        return testMapper.insert(order);
    }
}
