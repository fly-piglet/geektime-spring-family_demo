package geektime.spring.springbucks.jpacomplexdemo.repository;

import geektime.spring.springbucks.jpacomplexdemo.model.CoffeeOrder;

import java.util.List;

public interface CoffeeOrderRepository extends BaseRepository<CoffeeOrder, Long> {

    /**
     * 指定查询通过customer排序通过id
     * @param customer
     * @return
     */
    List<CoffeeOrder> findByCustomerOrderById(String customer);

    /**
     * 下划线，指明是内部子类的属性来处理, 使用功能下划线进行处理，进行区分
     * @param name
     * @return
     */
    List<CoffeeOrder> findByItems_Name(String name);

}
