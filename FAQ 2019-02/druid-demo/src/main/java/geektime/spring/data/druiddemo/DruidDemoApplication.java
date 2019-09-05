package geektime.spring.data.druiddemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * CommandLineRunner能够在容器运行完成后执行后处理
 */
@SpringBootApplication
@Slf4j
public class DruidDemoApplication implements CommandLineRunner {

	@Resource
	private DataSource dataSource;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private FooService fooService;

	public static void main(String[] args) {
		SpringApplication.run(DruidDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info(dataSource.toString());
		log.info(jdbcTemplate.toString());
		// 两个线程同时去查询，一个锁住了，另外一个就需要等待
		new Thread(() -> fooService.selectForUpdate()).start();
		new Thread(() -> fooService.selectForUpdate()).start();
	}
}
