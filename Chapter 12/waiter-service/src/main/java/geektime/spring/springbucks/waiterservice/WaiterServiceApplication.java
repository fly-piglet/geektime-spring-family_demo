package geektime.spring.springbucks.waiterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * 最新版本可以只使用对应的classpath来确定是用注册中心
 */
public class WaiterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaiterServiceApplication.class, args);
	}

}
