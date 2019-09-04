# 第二章节 Spring中的数据操作

## datasource-demo 配置单数据源

1. 使用内存数据库可以在resources中添加schema.sql和data.sql内存启动后会加载
2. 内存数据库的url: jdbc:h2:mem:testdb
3. 用户名：sa
4. 密码为空
5. 使用springboot的start依赖和自动注入就可以实现，在程序中自动注入datasource、jdbctemplate
6. 使用lombok注解@Slf4j就能够快速打印日志了, 默认springboot是有日志配置的
7. 使用jdbctemplate就能够进行部分高级查询，然后直接进行日志输出

## multi-datasource-demo 多数据源

1. 不同数据源配置要分开
2. 关注每次使用的数据源
   1. 有多个Datasource时候系统如何判断
   2. 对应设施（事务、ORM等）如何选择数据源
3. springboot中多数据源配置
   1. 配置@Primary类型的Bean
   2. 排除Springboot的自动配置，通过手动配置进行实现

## druid-demo durid的基本demo

1. 哪些好用的链接池
   1. durid
   2. hikari
      1. 字节码级别优化
      2. 大量小改进
2. 在springboot中的配置
   1. springboot2.x
      1. 默认使用hikariCp
      2. 使用hikari.*配置
   2. 1.x
      1. 默认使用tomcat连接池，需要移除tomcat-jdbc依赖
      2. 指定spring.datasource.type
3. durid
   1. 详细的监控页面
   2. ExecptionSorter,针对主流的数据库返回码都支持
   3. sql防止注入
   4. 内置加密配置
   5. 众多扩展点方便进行定制
4. 配置
   1. 配置filter
   2. 配置密码加密
   3. 配置sql防止注入
5. 通过Filter
   1. 用于定制连接池操作的各个环节
   2. 可以继承FilterEventAdapter以方便地实现Filter
   3. 修改durid-filter。properties增加Filter配置，使用spi的方式

## simple-jdbc-demo 基本jdbc的案例

1. spirng-jdbc
   1. core,jdbcTemplate等相关核心接口和类
   2. datasource,数据源相关类的辅助类
   3. object,将基本jdbc操作对象，封装成为对象
   4. support,错误码等其他辅助工具
2. 常用注解
   1. Component、Respository、Service、Controller、RestController
3. 简单的jdbc操作
   1. query
   2. queryForObject
   3. queryForList
   4. update
   5. execute

showme the code：simple-jdbc-demo

1. 模块名：geektime.spring.data
2. id: simple-jdbc-demo
3. 依赖：jdbc、h2、lombok
4. 无配置
5. 使用内存数据库，导入数据和脚本，使用jdbc默认配置
6. 编写dao、batchdao、application测试主要逻辑

学习重点

1. 通过Arrays.asList() 数组直接转换成为List
2. 插入得通用jdbcTemplate的update操作来处理，sql执行是使用update
3. 通过同居类SimpleJdbcInsert是能够获取工具相关的处理的。都需要提前处理
4. jdbctemplate提供了很多方便的方法，批处理，以及名字的批处理工具，在快速开发一个与数据库打交道的功能是很快能够上手的
5. 查询分为三种情况
   1. 查询一个数据
   2. 查询单值列表数据
   3. 查询多映射列表数据maprow

## 了解spring的抽象：事务抽象

1. 一致的事务模型
   1. jdbc/hibernate/mybatis
   2. datasource/jta
2. 事务抽象的核心接口
   1. PlatformTransactionManager
      1. DatasourceTransactionmanager: jdbc\mybatis
      2. HibernateTransactionmanager:hibernate
      3. JtaTransactionManager:jta
   2. TransactionDefinition
      1. propagation:传播特性（7种）
         1. 有就用当前没有就用新的（默认）
         2. 事务可有可无不是必须的
         3. 当前一定要有事务，不然抛出异常
         4. 无论是否有事务，都开启新事务
         5. 不支持事务，按照没有事务运行
         6. 不支持事务，如果有则抛出异常
         7. 当前有事务，就再启动一个事务
      2. isolation：隔离级别，解决问题：脏读、不可重复读、幻读
         1. 读未提交
         2. 读已提交
         3. 可重复读
         4. 串行化
      3. timeout：超时
      4. readonlystatus：只读模式
3. 编程式事务
   1. TransactionTemplate
      1. TranscationCallback
      2. TransactionCallbackWithoutResult
   2. PlatformTransactionManager
      1. 可以传入TranscationDefinition进行定义
4. 生命式事务：通过aop来实现的
   1. 开启事务注解的方式
      1. @EnableTranscationManagement
      2. xml配置：tx:annotation-driven
   2. 一些配置
      1. proxyTargetClass: 使用cglib的模式，无论是否是接口都能代理实现事务
      2. mode：
      3. order:
   3. 注解Transactional
      1. transactionManager
      2. propagation
      3. isolation
      4. timeout
      5. readonly
      6. 如何判断回滚：异常

## spirng jdbc的异常抽象

1. spring会将数据操作的异常转换为DataAccessException
2. 无论使用何种数据访问方式，都能使用一样的异常
3. spring是怎么认识那些错误码的
   1. 通过sqlerrorcodesqlexceptionTranslator解析错误码
   2. ErrorCode定义
      1. org/springframework/jdbc/support/sql-error-codes.xml
      2. Classpath下的sql-error-codes.xml
   3. 能通过配置文件实现对于错误嘛的定制化处理，并且能够定制化自己的异常，实现自定义数据库异常
