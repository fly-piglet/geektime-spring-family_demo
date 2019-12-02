package geektime.spring.data.simplejdbcdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootApplication
public class SimpleJdbcDemoApplication implements CommandLineRunner {

	@Resource
	private FooDao fooDao;
	@Resource
	private BatchFooDao batchFooDao;

	@Bean
	public SimpleJdbcInsert simpleJdbcInsert(JdbcTemplate jdbcTemplate) {
		// 绑定了表，并且通过绑定返回字段，通过map设定插入字段，实现插入功能。如果插入不成功直接报错
		return new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("foo").usingGeneratedKeyColumns("id");
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	public static void main(String[] args) {
		SpringApplication.run(SimpleJdbcDemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		fooDao.insertData();
		batchFooDao.batchInsert();
		fooDao.listData();
	}
}
