# nosql数据

## docker使用

容器和虚拟机不同，不包含操作系统的细节相关的东西，所以部署更快，启动容器更快。
在容器里面启动基础设施

* 认识docker
* client通过命令行和host的守护进程进行交互
  * 管理images将镜像生成容器
  * 私服

不同人眼中的docker
开发的docker: 简化重复搭建开发环境工作
运维眼中的docker
* 交付系统更为流畅
* 伸缩性更好

镜像相关：
docker pull
docker search
docker run 
docker start/stop
docker ps
docker logs

docker run
* -d 后台运行
* -e 设定环境变量
* --expose / -p 宿主端口：容器端口
* --name, 指定容器名称
* --link 链接不同的容器
* -v 映射宿主机目录

官方镜像
阿里云镜像

```sh
docker run --name mongo -p 27017:27017 -v ~/docker-data/mongo:/data/db -e xxxx -e xxx mangodb
```

nosql
kev value
文档型
列存储：hbase
图数据：neo4j
