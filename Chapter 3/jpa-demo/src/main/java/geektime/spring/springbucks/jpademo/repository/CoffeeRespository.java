package geektime.spring.springbucks.jpademo.repository;

import geektime.spring.springbucks.jpademo.model.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRespository extends CrudRepository<Coffee, Long> {
}
