package geektime.spring.data.declarativetransactiondemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@SpringBootApplication
@EnableTransactionManagement(
		mode = AdviceMode.PROXY,
		proxyTargetClass = true // 使用cglib进行代理实现
)
@Slf4j
public class DeclarativeTransactionDemoApplication implements CommandLineRunner {

	@Resource
	private FooService fooService;
	@Resource
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DeclarativeTransactionDemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		fooService.insertRecord();
		log.info("AAA {}", jdbcTemplate.queryForObject("select count(*) from foo where bar = 'AAA'", Long.class));

		// 事务回滚
		try {
			fooService.insertThenRollback();
		} catch (RollbackException e) {
			log.info("BBB {}", jdbcTemplate.queryForObject("select count(*) from foo where bar = 'BBB'", Long.class));
		}

		// 事务不回滚，内部调用没有经过切面
		try {
			fooService.invokeInsertThenRollback();
		} catch (RollbackException e) {
			log.info("BBB {}", jdbcTemplate.queryForObject("select count(*) from foo where bar = 'BBB'", Long.class));
		}
	}
}
