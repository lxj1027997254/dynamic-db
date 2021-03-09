package cn.lxj.db.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSource.class);

    /**
     * 获取数据源key
     * @return
     */
    protected Object determineCurrentLookupKey() {
        String dataSourceId = DynamicDataSourceContextHolder.getDataSourceId();
        if (dataSourceId == null) {
            dataSourceId = DynamicDataSourceRegister.DEFAULT_DATASOURCE_PREFIX;
        }
        LOGGER.info("Thread id: {}, DataSource id: [{}]", Thread.currentThread().getId(), dataSourceId);
        return dataSourceId;
    }
}
