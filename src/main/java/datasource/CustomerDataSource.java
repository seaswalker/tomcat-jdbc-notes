package datasource;

import configurator.bean.annotation.Component;
import configurator.bean.annotation.Init;
import configurator.bean.annotation.Value;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 * 自定义Tomcat jdbc dataSource实现.
 *
 * @author zhao.xudong
 */
@Component
public class CustomerDataSource {

    private PoolProperties properties = new PoolProperties();
    private DataSource dataSource;

    /**
     * 初始化连接池配置.
     */
    @Init
    public void initProperties() {
        properties.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        this.dataSource = new DataSource();
        this.dataSource.setPoolProperties(properties);
    }

    /**
     * 获取唯一的{@link DataSource}.
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    @Value(key = "dataSource.url")
    public void setUrl(String url) {
        properties.setUrl(url);
    }

    @Value(key = "dataSource.driverClassName")
    public void setDriverClassName(String driverClassName) {
        properties.setDriverClassName(driverClassName);
    }

    @Value(key = "dataSource.username")
    public void setUsername(String username) {
        properties.setUsername(username);
    }

    @Value(key = "dataSource.password")
    public void setPassword(String password) {
        properties.setPassword(password);
    }

    @Value(key = "dataSource.jxmEnabled")
    public void setJmxEnabled(boolean jmxEnabled) {
        properties.setJmxEnabled(jmxEnabled);
    }

    @Value(key = "dataSource.testWhileIdle")
    public void setTestWhileIdle(boolean testWhileIdle) {
        properties.setTestWhileIdle(testWhileIdle);
    }

    @Value(key = "dataSource.testOnBorrow")
    public void setTestOnBorrow(boolean testOnBorrow) {
        properties.setTestOnBorrow(testOnBorrow);
    }

    @Value(key = "dataSource.validationQuery")
    public void setValidationQuery(String validationQuery) {
        properties.setValidationQuery(validationQuery);
    }

    @Value(key = "dataSource.maxActive")
    public void setMaxActive(int maxActive) {
        properties.setMaxActive(maxActive);
    }

}
