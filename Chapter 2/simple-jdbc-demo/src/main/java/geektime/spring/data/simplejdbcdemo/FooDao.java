package geektime.spring.data.simplejdbcdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class FooDao {

    @Resource
    private JdbcTemplate jdbcTemplate;
    /**
     * 工具类，通过jdbcinser操作中，获取相关的参数
     */
    @Resource
    private SimpleJdbcInsert simpleJdbcInsert;


    public void insertData() {
        // 插入两条记录，插入居然使用update，按照sql的语法，只要是执行sql都是使用update
        Arrays.asList("a", "b").forEach(bar -> {
            jdbcTemplate.update("insert into foo(bar) values (?)", bar);
        });
        // 通过字段获取对应的id字段
        HashMap<String, String> row = new HashMap<>();
        row.put("bar", "d");
        Number id = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("id of d: {}", id.longValue());
    }

    public void listData() {
        // 计算统计：直接获取单个数据
        log.info("Count: {}", jdbcTemplate.queryForObject("select count(*) from foo", Long.class));
        // 获取单列数据
        List<String> list = jdbcTemplate.queryForList("select bar from foo", String.class);
        list.forEach(s -> log.info("bar:{}", s));
        // 计算多列数据，需要进行maprow进行映射
        List<Foo> fooList = jdbcTemplate.query("select * from foo", new RowMapper<Foo>() {
            @Override
            public Foo mapRow(ResultSet resultSet, int i) throws SQLException {
                return Foo.builder()
                        .id(resultSet.getLong(1))
                        .bar(resultSet.getString(2))
                        .build();
            }
        });
        fooList.forEach(f -> log.info("Foo: {}", f));
    }
}
