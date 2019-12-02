package geektime.spring.data.declarativetransactiondemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class FooServiceImpl implements FooService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertRecord() {
        jdbcTemplate.update("insert into foo(bar) values ('AAA')");
    }

    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public void insertThenRollback() throws RollbackException {
        // update是基于execute实现的，update可以带参数，execute是不能的
        jdbcTemplate.execute("insert into foo(bar) values ('BBB')");
        throw new RollbackException();
    }

    @Override
    public void invokeInsertThenRollback() throws RollbackException {
        insertThenRollback();
    }
}
