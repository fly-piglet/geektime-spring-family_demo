# 服务注册与发现

https://12factor.net/zh_cn/

api gateway 
service registry

microservice

redis
kafka
db

## spring cloud的主要功能

1. 服务发现
2. 服务网关
3. 服务熔断
4. 分布式消息
5. 配置服务
6. 分布式跟踪
7. 服务安全
8. 各种云平台支持

## 具体组件

Eureka作为注册中心

使用LoadBalancer访问服务

使用功能feign访问服务

zookeeper作为注册中心

consul作为注册中心

nacos作为服务注册中心

定制自己的discoverClient

通过多种服务注册中心注册服务，通过多种服务发现中心发现服务
