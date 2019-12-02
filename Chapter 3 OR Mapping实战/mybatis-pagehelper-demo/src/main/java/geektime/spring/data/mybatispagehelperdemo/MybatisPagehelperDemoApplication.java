package geektime.spring.data.mybatispagehelperdemo;

import geektime.spring.data.mybatispagehelperdemo.mapper.CofferMapper;
import geektime.spring.data.mybatispagehelperdemo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import com.github.pagehelper.PageInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
@MapperScan("geektime.spring.data.mybatispagehelperdemo.mapper")
@Slf4j
public class MybatisPagehelperDemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPagehelperDemoApplication.class, args);
	}


	@Resource
	private CofferMapper cofferMapper;

	@Override
	public void run(String... args) throws Exception {
		cofferMapper.findAllWithRowBounds(new RowBounds(1, 3))
			.forEach(c -> log.info("Page(1) Coffee {}", c));

		cofferMapper.findAllWithRowBounds(new RowBounds(2, 3))
				.forEach(c -> log.info("Page(2) coffee {} ", c));

		log.info("-------------------");

		cofferMapper.findAllWithParam(1, 3)
				.forEach(c -> log.info("Page(1) Coffee {}", c));

		List<Coffee> list = cofferMapper.findAllWithParam(2, 3);
		PageInfo page = new PageInfo(list);
		log.info("PageInfo: {}", page);

	}
}
