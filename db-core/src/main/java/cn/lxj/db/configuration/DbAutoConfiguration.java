package cn.lxj.db.configuration;

import cn.lxj.db.dynamic.DynamicDataSourceAspect;
import cn.lxj.db.dynamic.DynamicDataSourceAspectForClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 多数据源自动配置类
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class DbAutoConfiguration {

    @Bean
    public DynamicDataSourceAspect dynamicDataSourceAspect() {
        return new DynamicDataSourceAspect();
    }

    @Bean
    public DynamicDataSourceAspectForClass dynamicDataSourceAspectForClass() {
        return new DynamicDataSourceAspectForClass();
    }
}
