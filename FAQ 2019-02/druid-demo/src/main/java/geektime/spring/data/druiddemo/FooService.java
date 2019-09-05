package geektime.spring.data.druiddemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Repository
public class FooService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 需要锁表的时候，也必须在程序中添加事务的注解，这样数据库才是有事务的，否则默认jdbc事务是执行完就提交，没有事务。
     * 这边将数据库查询在程序中进行了慢查询实现，有事务才能够锁住数据库执行时间才会长。
     */
    @Transactional
    public void selectForUpdate() {
        // 通过竞争锁表的方式实现慢查询，十分优秀
        jdbcTemplate.queryForObject("select id from foo where id = 1 for update", Long.class);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

    }
}
