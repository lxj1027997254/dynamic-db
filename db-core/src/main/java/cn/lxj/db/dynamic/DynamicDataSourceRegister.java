package cn.lxj.db.dynamic;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态数据源注册器
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

    private static final String PREFIX = "dynamic.db";

    private static final String PREFIX_NAMES = "dynamic.db.names";

    public static final String DEFAULT_DATASOURCE_PREFIX = "default";

    /**
     * 数据源配置
     */
    private static final String DEFAULT_DATASOURCE_TYPE = "com.alibaba.druid.pool.DruidDataSource";
    private static final String DRIVER_CLASS_NAME = "driver-class-name";
    private static final String URL = "url";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    /**
     * 多数据源分隔符
     */
    private static final String REGEX = ",";

    /**
     * 默认数据源
     */
    private DataSource defaultDataSource;

    /**
     * 自定义数据源
     */
    private Map<String, DataSource> customDataSources = new HashMap<>();

    public void setEnvironment(Environment environment) {
        Map propertiesMap = getPropertiesMap(environment);
        String names = environment.getProperty(PREFIX_NAMES);
        for (String prefix : names.split(REGEX)) {
            Map<String, String> dsMap = (Map<String, String>) propertiesMap.get(prefix);
            DataSource ds = buildDataSource(dsMap);
            if (DEFAULT_DATASOURCE_PREFIX.equals(prefix)) {
                defaultDataSource = ds;
            } else {
                customDataSources.put(prefix, ds);
            }
        }
    }

    /**
     * 创建数据源
     *
     * @param dsMap
     * @return
     */
    private DataSource buildDataSource(Map<String, String> dsMap) {
        try {
            String type = dsMap.get("type");
            if (StringUtils.isBlank(type)) {
                // 默认druid数据源
                type = DEFAULT_DATASOURCE_TYPE;
            }
            Class<? extends DataSource> dataSourceType = (Class<? extends DataSource>) Class.forName(type);
            String driverClassName = dsMap.get(DRIVER_CLASS_NAME);
            String url = dsMap.get(URL);
            String username = dsMap.get(USERNAME);
            String password = dsMap.get(PASSWORD);
            DataSourceBuilder<? extends DataSource> dataSourceBuilder = DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password).type(dataSourceType);
            return dataSourceBuilder.build();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * 获取指定前缀配置的map
     *
     * @param environment
     */
    private Map getPropertiesMap(Environment environment) {
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);
        Binder binder = new Binder(sources);
        return binder.bind(PREFIX, Map.class).get();
    }


    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (defaultDataSource == null) {
            return;
        }
        Map<String, DataSource> targetDataSource = new HashMap<>();
        // 默认数据源
        targetDataSource.put(DEFAULT_DATASOURCE_PREFIX, defaultDataSource);
        DynamicDataSourceContextHolder.addDataSourceId(DEFAULT_DATASOURCE_PREFIX);
        targetDataSource.putAll(customDataSources);
        for (String prefix : customDataSources.keySet()) {
            DynamicDataSourceContextHolder.addDataSourceId(prefix);
        }

        // 创建DynamicDataSource的bean
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        beanDefinition.setPrimary(true);
        // 设置AbstractRoutingDataSource中数据源的映射关系
        // DynamicDataSource仅用于获取线程上下文的数据源的key
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        // determineCurrentLookupKey为null时取得默认数据源
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        // 所有数据源集合
        mpv.addPropertyValue("targetDataSources", targetDataSource);
        registry.registerBeanDefinition("dynamicDataSource", beanDefinition);
    }
}
