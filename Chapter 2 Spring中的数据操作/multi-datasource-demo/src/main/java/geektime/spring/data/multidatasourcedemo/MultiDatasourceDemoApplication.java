package geektime.spring.data.multidatasourcedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 一般配置多个数据源需要各自独立数据源以及事务管理器，并且需要设定一个为主要的事务管理器
 * spring默认自动依赖注入，所以需要去除自动配置的选项
 * 由于我们在配置文件中指定了，不同数据源的配置，从不同数据源获取的链接，就提交到不同数据源的数据库
 *
 * spring的原理就是有别人自定义就用别人的，没有就用自动配置的。
 *
 * 使用端点 '/actuator'获取系统状态
 * 使用json format实现格式解析
 */
@SpringBootApplication(
		exclude = {
				DataSourceAutoConfiguration.class,
				DataSourceTransactionManagerAutoConfiguration.class,
				JdbcTemplateAutoConfiguration.class,
		}
)
@Slf4j
public class MultiDatasourceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiDatasourceDemoApplication.class, args);
	}

	@Bean
	@ConfigurationProperties("foo.datasource")
	public DataSourceProperties fooDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("bar.datasource")
	public DataSourceProperties barDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public PlatformTransactionManager fooTxManager() {
		DataSource dataSource = fooDataSource();
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public PlatformTransactionManager barTxManager() {
		DataSource dataSource = barDataSource();
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public DataSource fooDataSource() {
		DataSourceProperties dataSourceProperties = fooDataSourceProperties();
		log.info("foo datasource {}", dataSourceProperties.getUrl());
		return dataSourceProperties.initializeDataSourceBuilder().build();
	}

	@Bean
	public DataSource barDataSource() {
		DataSourceProperties dataSourceProperties = barDataSourceProperties();
		log.info("bar datasource {}", dataSourceProperties.getUrl());
		return dataSourceProperties.initializeDataSourceBuilder().build();
	}
}
