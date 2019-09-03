package geektime.spring.data.datasourcedemo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@Slf4j
public class DatasourceDemoApplication implements CommandLineRunner {

	@Resource
	private DataSource dataSource;
	@Resource
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DatasourceDemoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		showConnection();
		showData();
	}
	
	private void showConnection() throws SQLException {
		log.info(dataSource.toString());
		Connection conn = dataSource.getConnection();
		log.info(conn.toString());
		conn.close();
	}

	private void showData() {
		jdbcTemplate.queryForList("select * from foo")
				.forEach(row -> log.info(row.toString()));
	}
}

