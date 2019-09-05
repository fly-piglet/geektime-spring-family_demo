# 答疑

## 需要的基础课程

1. 基本知识：java、java语法、java lamba
2. 对java常用的库和包了解
3. 常用库：slf4j、apache common了解
4. 工具：maven基本知识
5. sql：数据库rdbms基本的数据库写法
6. web有大概的了解
7. 有些东西监控会讲
8. 在后面的课程和原理有关系

## 章节

1. 原理比例：3-7（原理:实践）扩展口、定制
2. 章节设定：
   1. 概括
   2. 数据访问
   3. web
   4. springBoot
   5. 服务注册于发现（alibaba）

## 答疑内容清单

1. 开发环境的相关说明
2. 一些spring的常用注解简介
3. 关于actuator endpoints访问不到的说明
4. 多数据源，分库，分表，读写分离的关系
5. 与内部方法调用事务的课后问题
6. requires_new与nested事务的传播特性说明
7. alibaba druid的一些展开说明

## 开发环境

1. java8-11
2. idea社区版本安装自己做需要插件：
   1. ignore
   2. codeglance
   3. databasenavigator
   4. docker integration
   5. grep console
   6. key promoter x
   7. lombok
   8. markdown support
   9. maven helper
   10. plantuml integration
   11. restfulToolKit
   12. spring assistant
   13. spring manipulation
3. maven
4. docker
5. mac os majave

## spring 一些常用注解

配置注解：

1. Configuration（设定是配置类）
2. ImportResource（设定导入的spring.xml）
3. ComponentScan（自动扫描）
4. Bean（bean能够交给容器托管）
5. ConfigurationProperties（配置文件可以注入）

定义相关注解:

1. component/respository/service（定义bean）
2. controller/restcontroller（直接返回json）
3. requestMapping(方法url映射)

注入相关注解：

1. autowired(按照类型查找注入，同类型)/qualifier（配合名字进行注入）/resource(使用名字进行注入)
2. value（常量，表达式，上下文配置的东西）

## 关于actuator endpoints访问不到的说明

常用端点：

1. health:健康检查
2. beans: bean情况
3. mapings: url映射
4. env: 环境相关的配置

spring处于安全考虑：

1. 默认都是通过jms来访问的，一般通过web来访问的只有health和info
2. 我们可以通过配置来开启,在yml或者properties中来开启
   1. management.endpoints.web.exposure.include=*
3. 生成环境需要注意开启的endpoints，关闭的也会有，所以需要考虑

## 多数据源，分库，分表，读写分离的关系

1. 系统需要访问几个完全不同的数据库（业务不同的数据库）
2. 系统需要访问同一个库的主库与备库（读写分离）可以使用数据库中间件，可以在服务端，也可以才客户端请求入口
3. 系统需要访问一组做了分库分表的数据库（垂直拆分，水平拆分）
4. 数据库中间件：对于应用来说感觉是一个数据库，中间件来做请求的路由，dbproxy,带事务去主库，读备库，分库分离读写请求（建议使用数据库中间件）

## 与内部方法调用事务的课后问题

1. spring的声明式事务本质上是通过aop来增强了类的功能
2. apo本质就是为类做了一个代理
   1. 看似调用自己写的类，实际用的是增强后的代理类
3. 问题的解法
   1. 访问增强后的代理类的方法，而非直接访问自身的方法

## requires_new与nested事务的传播特性说明

1. requires_new始终启动一个新事务
   1. 两个事务没有关联（可以互相成功）
2. nested在原事务启动一个内嵌事务
   1. 两个事务有关联
   2. 外部事物回滚，内嵌事务也会回滚
   3. 内部回滚，外部不影响，该提交提交

## alibaba druid的一些展开说明

系统属性配置：

1. stat.logSlowSql=true
2. stat.slowSqlMillis=3000 设定时间
3. state.enable: 通过start是默认true的，所以不用配置

spirngboot配置导通小异：

一些注意事项：

1. 没特殊情况，不要在生产环境打开监控：生产环境是集群环境，一般不会每次都链接上去看的，一般也通过日志进行获取数据，不能安全保证黑客会不会入侵。负载均衡看到的也不准确
2. removeAbandoned:链接泄露的后续事情，但现在使用框架都不会有问题，这个一般关闭，性能会有影响
3. testXxxx的使用需要注意
4. 务必配置合理的超时时间（）有超时，系统才不会卡死

获取链接流程解析：

1. druidDatasource.getConnectionDirct，死循环获取链接
2. 死循环，通过配置调用，会记录很多信息，占用内存暂用时间，实际也很难遇到这样的情况。

扩展机制：

1. 去链接的动作，如果在去链接配置了filter去链接后会获得chain绑定了很多filter实现，责任链的设计模式，其他的也是一样的，我们可以使用各种各样的扩展。
