package geektime.spring.springbucks.jpacomplexdemo;

import geektime.spring.springbucks.jpacomplexdemo.model.Coffee;
import geektime.spring.springbucks.jpacomplexdemo.model.CoffeeOrder;
import geektime.spring.springbucks.jpacomplexdemo.model.OrderState;
import geektime.spring.springbucks.jpacomplexdemo.repository.CoffeeOrderRepository;
import geektime.spring.springbucks.jpacomplexdemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@Slf4j
public class JpaComplexDemoApplication implements ApplicationRunner {

	@Resource
	private CoffeeRepository coffeeRepository;
	@Resource
	private CoffeeOrderRepository orderRepository;

	public static void main(String[] args) {
		SpringApplication.run(JpaComplexDemoApplication.class, args);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	/**
	 * 有些时候，是需要添加事务的，不然事务会有问题
 	 */
	public void run(ApplicationArguments args) throws Exception {
		initOrders();
		findOrders();
	}

	private void initOrders() {
		// 创建咖啡的实例
		Coffee latte = Coffee.builder().name("latte")
				.price(Money.of(CurrencyUnit.of("CNY"), 30.0))
				.build();
		coffeeRepository.save(latte);
		log.info("Coffee: {}", latte);

		// 还有一个espresso
		Coffee espresso = Coffee.builder().name("espresso")
				.price(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.build();
		coffeeRepository.save(espresso);
		log.info("Coffee: {}", espresso);

		// 创建了两张订单
		CoffeeOrder order = CoffeeOrder.builder()
				.customer("Li Lei")
				// 只有espresso
				.items(Collections.singletonList(espresso))
				.state(OrderState.INIT)
				.build();
		orderRepository.save(order);
		log.info("Order: {}", order);

		order = CoffeeOrder.builder()
				.customer("Li Lei")
				// 有两个数据
				.items(Arrays.asList(espresso, latte))
				.state(OrderState.INIT)
				.build();
		orderRepository.save(order);
		// 插入了order表，以及order关系表
		log.info("Order: {}", order);
	}

	private void findOrders() {
		coffeeRepository
				.findAll(Sort.by(Sort.Direction.DESC, "id"))
				.forEach(c -> log.info("Loading {}", c));

		List<CoffeeOrder> list = orderRepository.findTop3ByOrderByUpdateTimeDescIdAsc();
		log.info("findTop3ByOrderByUpdateTimeDescIdAsc: {}", getJoinedOrderId(list));

		list = orderRepository.findByCustomerOrderById("Li Lei");
		log.info("findByCustomerOrderById: {}", getJoinedOrderId(list));

		// 不开启事务会因为没Session而报LazyInitializationException
		list.forEach(o -> {
			log.info("Order {}", o.getId());
			o.getItems().forEach(i -> log.info("  Item {}", i));
		});

		list = orderRepository.findByItems_Name("latte");
		log.info("findByItems_Name: {}", getJoinedOrderId(list));
	}

	private String getJoinedOrderId(List<CoffeeOrder> list) {
		return list.stream().map(o -> o.getId().toString())
				.collect(Collectors.joining(","));
	}
}