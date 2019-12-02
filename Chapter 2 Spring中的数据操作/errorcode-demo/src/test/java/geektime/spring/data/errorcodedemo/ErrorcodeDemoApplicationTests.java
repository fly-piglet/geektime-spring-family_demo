package geektime.spring.data.errorcodedemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ErrorcodeDemoApplicationTests {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Test(expected = CustomDuplicatedKeyException.class)
	public void testThrowingCustomException() {
		jdbcTemplate.execute("INSERT INTO FOO (ID, BAR) VALUES (1, 'a')");
		jdbcTemplate.execute("INSERT INTO FOO (ID, BAR) VALUES (1, 'b')");
	}
}
