package cn.lxj.db.annotation;

import cn.lxj.db.configuration.DbAutoConfiguration;
import cn.lxj.db.dynamic.DynamicDataSourceRegister;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.*;

/**
 * 多数据源开关
 * @author LXJ
 * @date 2021-2-14
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableTransactionManagement(proxyTargetClass = true)
@Import({DynamicDataSourceRegister.class, DbAutoConfiguration.class})
public @interface EnableDynamicDataSource {
}
