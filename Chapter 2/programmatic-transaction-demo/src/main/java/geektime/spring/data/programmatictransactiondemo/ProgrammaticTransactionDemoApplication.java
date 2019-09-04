package geektime.spring.data.programmatictransactiondemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

@SpringBootApplication
@Slf4j
public class ProgrammaticTransactionDemoApplication implements CommandLineRunner {

	@Resource
	private TransactionTemplate transactionTemplate;
	@Resource
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(ProgrammaticTransactionDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Count before transaction: {}", getCount());
		// 通过编程式事务插入数据，并且手动回滚，两次查询数据
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				jdbcTemplate.update("insert into foo(id, bar) values (1, 'aaa')");
				log.info("Count in transaction: {}", getCount());
				// 手动设定回滚状态
				transactionStatus.setRollbackOnly();
			}
		});
		// 事务的状态会是未提交的状态
		log.info("Count after transaction: {}", getCount());
	}

	private Long getCount() {
		return (Long) jdbcTemplate.queryForObject("select count(*) from foo", Long.class);
	}
}
