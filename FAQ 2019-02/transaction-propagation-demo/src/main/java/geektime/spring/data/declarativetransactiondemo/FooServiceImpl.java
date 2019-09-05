package geektime.spring.data.declarativetransactiondemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class FooServiceImpl implements FooService {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private FooService fooService;

    @Override
    public void insertRecord() {
        jdbcTemplate.update("insert into foo(bar) values ('AAA')");
    }

    /**
     * 事务的隔离级别中有两种情况都会有两个事务
     * 1. 一个是开启一个新事务，两者互相独立:REQUIRES_NEW
     *  1. 都互相不影响
     * 2. 开启内嵌事务
     *  1. 内嵌事务回滚，外部事务不影响
     *  2. 外部事务回滚，内嵌事务也回滚
     */
    @Override
    @Transactional(rollbackFor = RollbackException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertThenRollback() throws RollbackException {
        // update是基于execute实现的，update可以带参数，execute是不能的
        jdbcTemplate.execute("insert into foo(bar) values ('BBB')");
//        throw new RollbackException();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void invokeInsertThenRollback() throws RuntimeException {
        jdbcTemplate.execute("insert into foo(bar) values ('BBB')");
        try {
            // 内部是一个新事务
            fooService.insertThenRollback();
        } catch (RollbackException e) {
            log.error("RollbackException", e);
        }
//        throw new RuntimeException();
    }
}
