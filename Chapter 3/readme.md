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

## 通过JPA操作数据

* enableJpaRespositories
* 接口Repository<T, ID>接口
  * curdRepository
  * PagingAndSortingRepository
  * JpaRepository

定义查询，根据方法名定义

1. findby/readby/queryby/getby
2. countby
3. orderby asc desc
4. and or ignoreCase
5. top first distinct

分页查询
PagingAndSortingRepository
Pageable/Sort
Slice/Page
