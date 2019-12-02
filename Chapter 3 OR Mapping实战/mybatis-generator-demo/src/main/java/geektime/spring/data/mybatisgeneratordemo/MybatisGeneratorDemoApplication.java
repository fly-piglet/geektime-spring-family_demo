package geektime.spring.data.mybatisgeneratordemo;

import geektime.spring.data.mybatisgeneratordemo.mapper.CoffeeMapper;
import geektime.spring.data.mybatisgeneratordemo.model.Coffee;
import geektime.spring.data.mybatisgeneratordemo.model.CoffeeExample;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
@MapperScan("geektime.spring.data.mybatisgeneratordemo.mapper")
public class MybatisGeneratorDemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MybatisGeneratorDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		generateArtifacts();
		playWithArtifacts();
	}

	private void generateArtifacts() throws Exception {
		// 创建warning的字符串数组
		List<String> warnings = new ArrayList<>();
		// 创建配置解析器，添加数组
		ConfigurationParser cp = new ConfigurationParser(warnings);
		// 通过配置文件解析配置
		Configuration config = cp.parseConfiguration(
				this.getClass().getResourceAsStream("/generatorConfig.xml"));
		// 定义默认shell回调
		DefaultShellCallback callback = new DefaultShellCallback(true);
		// 创建mybatis的生成器
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		// 调用mybatis的生成方法
		myBatisGenerator.generate(null);
	}


	@Resource
	private CoffeeMapper coffeeMapper;

	private void playWithArtifacts() {
		Coffee espresso = new Coffee()
				.withName("espresso")
				.withPrice(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.withCreateTime(new Date())
				.withUpdateTime(new Date());

		coffeeMapper.insert(espresso);

		Coffee latte = new Coffee()
				.withName("latte")
				.withPrice(Money.of(CurrencyUnit.of("CNY"), 30.0))
				.withCreateTime(new Date())
				.withUpdateTime(new Date());
		coffeeMapper.insert(latte);

		Coffee coffee = coffeeMapper.selectByPrimaryKey(1L);
		log.info("coffee : {}", coffee);

		CoffeeExample example = new CoffeeExample();
		example.createCriteria().andNameEqualTo("latte");
		List<Coffee> list = coffeeMapper.selectByExample(example);
		list.forEach(e -> log.info("selectByExample: {}", e));
	}
}
