## datasource-demo

单数据源
使用内存数据库可以在resources中添加schema.sql和data.sql内存启动后会加载
内存数据库的url: jdbc:h2:mem:testdb
用户名：sa
密码为空

加入了starter
1. jdbc，会自动按照配置生成数据源，我们使用DataSource和JdbcTemplate就能够使用了
2. 使用lombok注解@Slf4j就能够快速打印日志了
3. 使用jdbctemplate就能够进行部分高级查询，然后直接进行日志输出