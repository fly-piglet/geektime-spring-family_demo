package geektime.spring.data.simplejdbcdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class BatchFooDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void batchInsert() {

        // 进行批量插入，不过这边的插入是在内存中拼接好字符串，一次性执行的
        jdbcTemplate.batchUpdate("insert into foo (bar) values (?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        // 可以设定随机字符串
                        preparedStatement.setString(1, "b-" + i);
                    }

                    @Override
                    public int getBatchSize() {
                        return 10;
                    }
                }
        );

        // 使用名字索引的工具类，来进行插入使用名字参数匹配的jdbctemplate
        List<Foo> list = new ArrayList<>();
        list.add(Foo.builder().id(1000001L).bar("b-10").build());
        list.add(Foo.builder().id(100000L).bar("b-100").build());
        namedParameterJdbcTemplate.batchUpdate("insert into foo (id, bar) values (:id, :bar)",
                SqlParameterSourceUtils.createBatch(list));
    }


}
