package cn.lxj.db.dynamic;

import java.util.ArrayList;
import java.util.List;

/**
 * 多数据源线程独享数据源id
 */
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    private static List<String> dataSourceIds = new ArrayList<>();

    public static void setDataSourceId(String dataSourceId) {
        contextHolder.set(dataSourceId);
    }

    public static String getDataSourceId() {
        return contextHolder.get();
    }

    public static void clearDataSourceId() {
        contextHolder.remove();
    }

    public static boolean containsDataSource(String dsId) {
        return dataSourceIds.contains(dsId);
    }

    public static void addDataSourceId(String dsId){
        dataSourceIds.add(dsId);
    }
}
