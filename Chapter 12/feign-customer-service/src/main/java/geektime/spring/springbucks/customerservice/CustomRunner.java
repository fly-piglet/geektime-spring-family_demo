package geektime.spring.springbucks.customerservice;

import com.netflix.discovery.EurekaClient;
import geektime.spring.springbucks.customerservice.integration.OrderServcie;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
@Slf4j
public class CustomRunner implements ApplicationRunner {

    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private OrderServcie orderServcie;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        showServiceInstances();
        readMenu();
        orderService();
        Long id = orderCoffee();
        queryOrder(id);
    }

    private void showServiceInstances() {
        log.info("DiscoveryClient：{}", discoveryClient.getClass().getName());
        discoveryClient.getInstances("waiter-service").forEach(s -> {
            log.info("Host: {}, Port: {}", s.getHost(), s.getPort());
        });
    }

    private void readMenu() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://waiter-service/order/getOrder", String.class);
        String body = forEntity.getBody();
        log.info("调用的返回请求为: {}", body);
    }

    private void orderService(){
        String order = orderServcie.getOrder();
        log.info("orderServcie: 调用的返回请求为: {}", order);

    }

    private Long orderCoffee() {
        return Long.valueOf(0);
    }

    private void queryOrder(Long id) {

    }
}
