package cn.lxj.db.dynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 多数据源线程独享数据源id
 */
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<Stack<String>> contextHolder = new ThreadLocal<>();

    private static List<String> dataSourceIds = new ArrayList<>();

    public static void setDataSourceId(String dataSourceId) {
        if (contextHolder.get() == null) {
            contextHolder.set(new Stack<>());
        }
        contextHolder.get().push(dataSourceId);
    }

    public static String getDataSourceId() {
        if (contextHolder.get() == null) {
            contextHolder.set(new Stack<>());
        }
        return contextHolder.get().empty() ? null : contextHolder.get().pop();
    }

    public static void clearDataSourceId() {
        contextHolder.get().clear();
    }

    public static boolean containsDataSource(String dsId) {
        return dataSourceIds.contains(dsId);
    }

    public static void addDataSourceId(String dsId) {
        dataSourceIds.add(dsId);
    }
}
