package cn.lxj.db.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 多数据源自动配置类
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class DbAutoConfiguration {

}
