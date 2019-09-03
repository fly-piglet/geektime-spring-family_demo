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

	public static void main(String[] args) {
		SpringApplication.run(DruidDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info(dataSource.toString());
		log.info(jdbcTemplate.toString());
	}
}
