package geektime.spring.data;


import javafx.application.Application;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.xml.transform.Source;
import java.awt.image.DataBuffer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

/** 生命配置类 */
@Configuration
/** 开启事务 */
@EnableTransactionManagement
public class DataSourceDemo {

    @Resource
    private DataSource dataSource;

    @Bean
    public DataSource dataSource() throws Exception {
        // 构造配置
        Properties properties = new Properties();
        properties.setProperty("url", "jdbc:h2:mem:testdb");
        properties.setProperty("username", "sa");
        properties.setProperty("driverClassName", "org.h2.Driver");
        // 通过jar包dbcp提供的工厂类，直接创建线程池
        return BasicDataSourceFactory.createDataSource(properties);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        // 由于开启事务了，需要通过数据源构造事务管理器
        return new DataSourceTransactionManager(dataSource);
    }

    public static void main(String[] args) throws SQLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext*.xml");
        // 显示容器中的所有bean，能够看到dataSource, transactionManager
        showBean(applicationContext);
        // 由于bean需要加载，所以这边从容器中获取，来显示数据
        dataSourceDemo(applicationContext);
    }

    private static void dataSourceDemo(ApplicationContext applicationContext) throws SQLException {
        DataSourceDemo bean = applicationContext.getBean(DataSourceDemo.class);
        bean.showDataSource();
    }

    private void showDataSource() throws SQLException {
        System.out.println(dataSource.toString());
        Connection connection = dataSource.getConnection();
        System.out.println(connection.toString());
    }

    private static void showBean(ApplicationContext applicationContext) {
        // Arrays.toString是一个好方法，能够将数组直接打印出来
        System.out.println(Arrays.toString(applicationContext.getBeanDefinitionNames()));
    }


}
