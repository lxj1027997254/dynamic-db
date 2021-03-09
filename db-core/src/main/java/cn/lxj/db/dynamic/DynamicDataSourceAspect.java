package cn.lxj.db.dynamic;

import cn.lxj.db.annotation.TargetDataSource;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

@Aspect
@Order(-1)
public class DynamicDataSourceAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(targetDataSource)")
    public void changeDataSource(TargetDataSource targetDataSource) {
        String dsId = targetDataSource.value();
        if (StringUtils.isBlank(dsId)) {
            return;
        }
        if (DynamicDataSourceContextHolder.containsDataSource(dsId)) {
            DynamicDataSourceContextHolder.setDataSourceId(dsId);
        } else {
            LOGGER.error("数据源：{}不存在", dsId);
        }
    }

    @After("@annotation(targetDataSource)")
    public void resetDataSource(TargetDataSource targetDataSource) {
        DynamicDataSourceContextHolder.clearDataSourceId();
    }
}
