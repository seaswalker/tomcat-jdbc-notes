package datasource;

import configurator.bean.BeanContainer;
import configurator.conf.PropertiesSource;
import configurator.conf.exception.LoadException;
import configurator.inject.Injector;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 测试{@link org.apache.tomcat.jdbc.pool.DataSource}的相关操作.
 *
 * @author zhao.xudong
 */
public class DataSourceTest {

    private DataSource dataSource;

    @Before
    public void before() throws LoadException {
        PropertiesSource source = new PropertiesSource("classpath:data-source.properties");
        BeanContainer beanContainer = new Injector().basePackage("datasource").source(source).inject();
        this.dataSource = beanContainer.get(CustomerDataSource.class).getDataSource();
    }

    @Test
    public void testQuery() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from test.person");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") + ", name: "
                        + resultSet.getString("name"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }

    @After
    public void after() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

}
