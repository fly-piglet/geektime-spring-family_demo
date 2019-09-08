# ORM 相关的实践

## 对象与关系的范式不匹配

Object 与 RDBMS的对比：

1. 粒度： 类/表
2. 继承： 有/ 没有
3. 唯一性: a == b a.equals(b) / 主键
4. 关联：引⽤/外键
5. 数据访问：逐级访问/ SQL 数量要少

## Hikernate

* ⼀款开源的对象关系映射（Object / Relational Mapping）框架
* 将开发者从 95% 的常⻅数据持久化⼯作中解放出来
* 屏蔽了底层的数据库细节

发展历程

* 2001年第一个版本
* 2003年，hibernate开发团队成员加入JBoss
* Hibernate3.2成为了Jpa实现

JPA： Java Persistence API：对象关系映射提供基于POJO的持久化模型

* 简化数据持久化代码的开发工作
* 为Java社区屏蔽不同持久化api的差异
* 2006年Jpa1.0作为jsr220的一部分正式发布

Spring Data: 在保留底层存储特性的同时，提供相对一致的、基于Spring的编程模型

主要模块：

* spring data common
* spring data jdbc
* spring data jpa
* spring data redis
* redis
* ....

定义JPA实体对象

常用Jpa注解

实体：

* entity：这个类是实体, mappedSuperclass：父类中标注是父类
* table(name)：表关联起来

主键

* id：自定义id
* generatedValue(strategy, generator)：自动生成组件
* SequenceGenerator(name, sequenceName)：使用序列生成

映射

* Column(name, nullable, length,insertable, updateable) // 定义属性和表的映射关系，是否可以新增，是否可以修改
* JoinTable(name) joinColumn(name)// 关联表、关联字段

关系

* OneToOne、OneToMany、ManyToOne、ManyToMany：关联关系
* OrderBy：排序

Lombok的相关注解

* getset、tostring、noargsConstructor（默认构造函数）、requiredArgsConsructor 需要构造参数、allArgsConstructor（全构造函数）、data、builder（build的方式创建对象）、slf4j、commonslog、log4j2 日志相关

## 线上咖啡馆实战

* 实体：咖啡、订单、客户、服务员、咖啡师
* 关系
  * 一个客户对多个订单
  * 一个服务员对多个订单
  * 一个订单对应多杯咖啡
  * 一个订单对应多个咖啡制作
* 状态：
  * 可取消：初始、已支付、取消
  * 不可取消：制作中、制作完毕、已取货
* 涉及对象: 客户、店员、咖啡制作者

使用金融相关的数值，引入库来处理, 对于金额的处理一定要小心再小心
joda-money
jadira.usertype
lombok
datajpa

订单、咖啡、咖啡用户等的关系
使用toString添加callsuper为true

## 通过JPA操作数据

* enableJpaRespositories
* 接口Repository<T, ID>接口
  * curdRepository
  * PagingAndSortingRepository
  * JpaRepository

定义查询，根据方法名定义

1. findby/readby/queryby/getby： 查询
2. countby：计数
3. orderby asc desc： 排序
4. and or ignoreCase： 并且，忽略大小写
5. top first distinct： 去重，前几个，开始几个定义

分页查询
PagingAndSortingRepository，子接口
Pageable/Sort，分页和排序
Slice/Page

## Repository是怎么从接口变成bean的呢

* JPaRepositoriesRegister
  * 激活了@EnableJpaRepositories
  * 返回了JpaRepositoryConfigExtension
* RepositoryBeanDefinitionRegistrarSupport.registerBeanDefinitions
  * 注册RepositoryBean,类型是JpaRepositoryFactoryBean
* RepositoryBeanDefinitionRegistrarSupport.getRepositoryConfigurations
  * 取得Repository配置
* JpaRepositoryFacotry.getTargetRepository
  * 创建了Repository

总结其实就是通过spring烧苗对应的注解，获取元数据配置
注册，获取配置，获取具体配置，通过工厂的方式来创建实现类

## 接口的方法是如何被解释的

* RepositoryFacotrySupport.getRepository添加Advice
  * DefaultMethodInvokingMethodIntercetor
  * QueryExecutorMethodInterceptor
* AbstractJpaQuery.execute执行具体的查询
* 语法解析在Part中，在springboot的jpa的common中，语法解析树

在创建实现类的时候，添加过滤器拦截器，对针对的方法进行过滤和拦截，并且在通用方式中调用实现的接口

## 通过mybatis操作数据库

* Mybatis
  * 一款优秀的持久层框架
  * 支持定制化sql，存储过程，以及高级映射
* 在Spring中使用Mybatis
  * mybatis的psirng适配器
  * mybatis的springboot的starter
* 与hibernate如何选择
  * 如果整个操作简单的话，可以使用jpa
  * 如果dba对sql有比较高的要求，要加优化，或者本身比较复杂的时候，使用mybatis是比较合适的
  * 在bat的大厂里面有dba，所以跟多倾向于使用mybatis

简单配置：

1. mapper-locations=配置xml的map路径，一般是基于classpath下面的mapper文件夹，下面的所有xml都加载
2. 定义类型别名的包名:
3. 定义typeHandler扫描的包名
4. 定义配置的map其他相关的东西：将下划线转换为驼峰规则

定义与扫描：

1. @MapperScan配置扫描位置：扫描代码中对应的mapper的路径
2. @Mapper定义接口：定义接口，通过注解的形式配置xml实现以及映射结果
3. 映射的定义--xml与注解：使用xml描述对应的映射，同时混用

## 案例实现

1. 添加一个springdemo，添加mybatis的依赖，添加金钱库的依赖
2. 创建数据库实体coffee
3. 创建表结构，使用h2数据库在启动后自动创建
4. 创建money的handler转换器，提供money的转换
5. 添加呀一个mapper，使用注解方式实现保存和查询功能

## 使用mybatis代码生成器

Mybatisgenerator生成工具

* Mybatis代码生成器
* 根据数据库表生成相关代码
  * POJO
  * Mapper接口
  * SQLMapXml
* 运行MybatisGenerator
  * java -jar mybatis-generator-core-x.x.x.jar -configfile generatorcConfig.xml
  * 使用maven插件
    * mvn mybatis-generator:generate
    * ${basedir}/src/main/resources/generatorConfig.xml
  * Eclipse plugin
  * Java程序
  * Ant Task
* 配置Mybatis Generator
  * generatorConfiguration
  * context
    * jdbcConnection
    * javamodelGenerator
    * sqlMapGenerator
    * javaClientGenerator(注解，xml，都可以)
    * table
* 生成时候可以使用的插件
  * FluentBuilderMethodsPlugin
  * ToStringPlugin
  * SerializablePlugin
  * RowBoundsPlugin
  * ...
* 使用生成对象
  * 简单操作，直接使用生成的xxxMapper的方法
  * 复杂查询，使用生成的xxxExample对象
  * 又要组件又要自己的，所以自己分开放置，保证下次能够重新生成

## 例子

* 添加core依赖
* 第一步是生成，第二部是演示
* 添加表结构
* 添加配置，编写生成代码
* 编写实现代码
* 最佳实践，生成的代码和自己维护的代码分开编写，比如自己一套domain、mapper、xml这样可以直接替换。

## 认识mybatis分页插件

* 支持多种数据库
* 支持多种分页方式
* springboot原生支持
* 