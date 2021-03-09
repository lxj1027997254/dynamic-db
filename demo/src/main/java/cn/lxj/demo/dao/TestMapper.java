package cn.lxj.demo.dao;

import cn.lxj.demo.domain.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by LXJ on 2021-03-09.
 */
@Mapper
public interface TestMapper {
    Integer insert(Order order);
}
