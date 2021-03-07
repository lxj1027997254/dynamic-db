package cn.lxj.db.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 动态获取数据源key
     * @return
     */
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
