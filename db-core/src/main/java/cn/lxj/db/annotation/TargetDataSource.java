package cn.lxj.db.annotation;

import java.lang.annotation.*;

/**
 * 指定数据源
 * @author LXJ
 * @date 2021-2-14
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

    String value() default "";
}
