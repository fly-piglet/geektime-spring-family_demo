package geektime.spring.springbucks.customerservice.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "waiter-service", contextId = "order")
public interface OrderServcie {

    @GetMapping("/order/getOrder")
    String getOrder();

}
