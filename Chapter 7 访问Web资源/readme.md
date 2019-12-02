通过RestTemplate访问资源

web依赖之后，使用配置方式注入RestTemplate
提供了建造者模式来创建

常用方法：
get请求：getForObjet，getForEntity
post请求: postForObject, postForEntity
put请求：put
delete请求：delete

传递uri来构建
构造URI：URIComponentsBuilder
构造相对于当前请求的URI：ServletUriComponentsBuilder
构造指向Controller的URI：MvcUriComponentsBuilder

高阶用法：
传递HTTPHeader
通过够着对应的header来传递keyvalue的值
RestTemplate.exchange()返回值：RequestEntity<T> ResponseEntity<T>

高阶用法
类型转换：
JsonSerializer / JsonDeserializeri
@JsonComponent

解析泛型对象：
RestTemplate.exchange()
ParameterizedTypeReference<T>

简单定制RestTemplate
ClientHttpRequestFactory
默认实现：SimpleClientHttpRequestFactory
支持的库：
apache HttpComponents:HttpComponentsClientHttpRequestFactory
netty:Netty4ClientHttpRequestFactory
okHttp:Okhttp3ClientHeepRequestFactory

优化底层请求策略
连接管理
PoolingHttpClientConnectionManager
KeepAlive策略
超时设置
connectTimeout/ readTimeout
SSL校验
证书检查策略

使用策略实现链接复用


WeebClient是一个以Reactive方式处理Http请求的非阻塞式的客户端
支持的http底层库
Reactor Netty
Jetty httpClient
