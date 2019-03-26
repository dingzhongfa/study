#Spring-Cloud应用与实践

## 引入背景

我们的技术架构经历了三个阶段的变更，在`1.0`时期，应用的单体架构。通俗的讲就是将应用程序的所有功能都打包成一个独立的单元，可以是JAR、WAR、EAR或其它归档格式。随着业务量的增长，模块的增加，单体架构的缺点越来越明显。于是我们对技术架构做了一次大调整。由单体架构转向以dubbo为rpc框架的SOA架构。

单体架构的优势如下：
1. 容易部署
2. ide友好
3. 易于测试
4. 模块间无网络波动影响

但是相对于缺陷，优势的分量就轻的多了：
1. 项目启动慢，开发阶段不易调试
2. 项目启动慢，不易于频繁部署
2. 技术栈受限
3. 不够灵活，牵一发而动全身

因此我们转入`2.0`的基于dubbo的SOA时期。

随着业务量的持续增长，现有的架构虽足以满足需求，但是我们希望拆分的细粒度更细，同时，我们决定选用spring-cloud来实现服务综合治理。

很多人都拿dubbo和springcloud来做比较，我认为这种比较其实是不合理的，服务综合治理仅仅是spring-cloud中的一个模块（spring-cloud-netflix），真正作为对比的应该是dubbo和此模块。

spring-cloud为分布式架构提供了更多的支持和实现，我们简单罗列如下：
* Spring Cloud Config，配置中心，利用 git 集中管理程序的配置。
* Spring Cloud Netflix，集成众多 Netflix 的开源软件。
* Spring Cloud Bus，消息总线，利用分布式消息将服务和服务实例连接在一起，用于在一个集群中传播状态的变化 。
* Spring Cloud for Cloud Foundry，利用 Pivotal Cloudfoundry 集成你的应用程序。
* Spring Cloud Foundry Service Broker，为建立管理云托管服务的服务代理提供了一个起点。
* Spring Cloud Cluster，基于 Zookeeper、Redis、Hazelcast、Consul 实现的领导选举和平民状态模式的抽象和实现。
* Spring Cloud Consul，基于 Hashicorp Consul 实现的服务发现和配置管理。
* Spring Cloud Security，在 Zuul 代理中为 OAuth2 rest 客户端和认证头转发提供负载均衡。
* Spring Cloud Sleuth Spring Cloud，应用的分布式追踪系统和 Zipkin、HTrace、ELK 兼容。
* Spring Cloud Data Flow，一个云本地程序和操作模型，组成数据微服务在一个结构化的平台上。
* Spring Cloud Stream，基于 Redis、Rabbit、Kafka 实现的消息微服务，简单声明模型用以在 Spring Cloud 应用中收发消息。
* Spring Cloud Stream App Starters，基于 Spring Boot 为外部系统提供 Spring 的集成。
* Spring Cloud Task，短生命周期的微服务，为 Spring Boot 应用简单声明添加功能和非功能特性。
* Spring Cloud Task App Starters。
* Spring Cloud Zookeeper，服务发现和配置管理基于 Apache Zookeeper。
* Spring Cloud for Amazon Web Services，快速和亚马逊网络服务集成。
* Spring Cloud Connectors，便于 PaaS 应用在各种平台上连接到后端像数据库和消息经纪服务。
* Spring Cloud Starters，项目已经终止并且在 Angel.SR2 后的版本和其他项目合并。
* Spring Cloud CLI，插件用 Groovy 快速的创建 Spring Cloud 组件应用。

这个数量还在增加...

而对比`spring-cloud-netflix`和`dubbo`，具体不详细赘述，各个博客网站许多的spring-cloud和dubbo的比较说的就是这两者，仅netfix模块功能就较为丰富（如网关服务），同时，该模块与spring-cloud的其他自模块能够无缝兼容。日前，dubbo开发团队称会积极寻求适配spring-cloud生态，我们可以期待一下。

so，我们由此转入`3.0`时期，基于spring-cloud的微服务架构。


## Spring Cloud简介

Spring Cloud是一个基于Spring Boot实现的分布式系统，提供了一系列的常用工具（配置管理、服务发现、断路器、智能路由、微代理、控制总线、全局锁、决策竞选、分布式会话和集群状态管理），简化了开发难度，提升了工作效率。等操作提供了一种简单的开发方式。
### config（配置中心）

由于是微服务，就存在多个服务共存的情况，从而就存在传统配置方式难用的问题，更改一个配置可能就是一项不小的劳动力。我们可能需要将配置文件抽取出来单独管理，而Spring Cloud Config就是提供配置相关的解决方案的。其可通过SVN或Git的方式，实现服务配置的动态化，降低了由于配置问题而引发的一系列问题的成本。

基于使用Git方式存储代码，Config服务也就选择Git方案。为防止一些隐私配置的泄漏，Config也提供了加密方案。由于Config服务不存在高并发压力的服务，所以可能存在这样的一个场景。多个微服务项目共用同一个Config服务，由于目前是不支持动态生效配置，每次新增一个项目就必须停Config服务，存在风险，后期可能将在原有基础上进行实现一套动态生效配置功能。

### Registry（服务注册中心）

Registry选择了Netflix的注册服务Eureka。Eureka是Netflix开源的一款提供服务注册和发现的产品，它提供了完整的Service Registry和Service Discovery实现。当然也是支持高可用的。

说到Eureka不得不提一下Dubbo时代的Zookeeper服务注册方式，这两者都提供服务注册功能，在设计上就已经决定这两者的区别，CAP定理中（C-数据一致性；A-服务可用性；P-服务对网络分区故障的容错性）Zookeeper属于CP，Eureka则属于AP。而且Eureka在单一功能上，又提供了心跳服务、服务健康监测、自动发布服务与自动刷新缓存的功能，在开发中更方便我们查找服务的问题。

Eureka我们并没有进行深度的拓展，还是保留在其提供的功能上。出于安全考虑，使用了其提供的认证功能，在一定程度保证了安全。

### C Gateway（网关）

Gateway组件是Zuul通过实现鉴权、公共功能、限流、服务代理等功能进行系统的解耦。在实际应用中，我们不采用手动更改配置的方式去进行服务的管理，由于我们所有服务都是Spring Boot，可以使用`PatternServiceRouteMapper`将路由方式改为获取Eureka中的服务列表的动态方式。

`ZuulFilter`作为过滤器实现一些公共业务功能的开发和权限的控制，在公司的新版用户权限中心就是运用这种方式进行实现，进行Token的校验、接口权限的校验、角色Code和UserId的解析。

### Monitor（监控中心）

Monitor组件集成了Spring Boot Admin服务提供了注册在注册中心的服务的一些功能和性能指标的可视化功能。当然在Admin自带的功能无法满足我们复杂的实际场景，我们也进行了一些拓展，具体的在下文有详细说明。

Spring Boot本身也自带了例如`/health`和`/info`等指标接口，但这里存在一个安全问题，这些接口默认是开放的，不存在权限的限制，任何人都可以进行通过`HTTP`方式访问，我们也提供了一个解决方案，在后文中也有提及。

## 对Spring Cloud的优化


### 服务调用开发期优化

在Spring Cloud体系下，服务调用需要启动`Eureka`服务（对于Dew中的`Regstry`组件），这对开发阶段并不友好：

* 开发期间会不断启停服务，`Eureka` 保护机制会影响服务注册（当然这是可以关闭的）
* 多人协作时可能会出现调用到他人服务的情况（同一服务多个实例）
* 需要启动 `Eureka` 服务，多了一个依赖

为解决上述问题，我们做了相应的优化，
在服务调用时，我们会根据传入的 `URL` 判断，
如果是 `IPv4` 则直接调用服务，否则使用Spring Cloud的 `RestTemplate` 调用。

### `hystrix` 降级增加邮件通知


 为能更及时的对服务异常做出处理， 我们增加了邮件通知功能。

继承`HystrixEventNotifier`，重写`markEvent`方法，在`markEvent`方法中加以逻辑处理，设置可配的通知时间间隔。

```yaml
# 通知条件配置示例
dew:
  cloud:
    error:
      enabled: true
      notify-interval-sec: 1800
      notify-emails: 71964899@qq.com
      notify-event-types: FAILURE,SHORT_CIRCUITED,TIMEOUT,THREAD_POOL_REJECTED,SEMAPHORE_REJECTED
      notify-include-keys: ["ExampleClient#deleteExe(int,String)","ExampleClient#postExe(int,String)"]

# 邮箱配置示例
spring:
  mail:
    host: smtp.163.com
    username: <邮件地址>
    password: <password为smtp授权码，非邮箱密码>
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
```

###  `服务API调用` （追踪）日志处理


用于记录 `服务API调用` （追踪）日志到 `Slf4j`。

```yaml
dew:
  cloud:
    trace-log:
        enabled: true # 默认为true
```

之后可选择 `ELK` 方案，或是 `Dew`（自主研发框架） 推荐的 `EK` 方案（跳过 `Logstash`，直接向 `ES` 提交）

一次调用日志的查看，以 `ES` 为例，过滤条件是: logger:com.tairanchina.csp.dew.core.logger.DewTraceLogWrap & trace:<对应的traceID>

### metrics查询

基于``Spring Boot Actuator``提供的metrics接口，`dew`（自主研发框架）增加了三个TPS指标，最大响应时间，平均响应时间，90%的响应时间。

NOTE: 开发者可以get请求访问根路径下的/metrics接口，即可看到新增的 `dew.response.nityPercent`,`dew.response.average`,`dew.response.max`,`dew.response.tps` 四个指标
      及针对接口的对应指标,生产或测试环境中多以登陆admin服务来查看统计数据

在`dew 1.3.0`后metrics指标增加线程、内存、cpu、磁盘等统计


```yaml
# 启用metrics接口
endpoints:
  metrics:
    enabled: true

# spring默认该接口需要security拦截，否则会提示``Unauthorized``，加以下配置即可
management:
  security:
    enabled: false

# 指定统计周期（多少秒内的指标统计）
dew:
  metric:
    period-sec: # 默认600s(10分钟),（单位秒）
```

开发者可以get请求访问根路径下的/metrics接口，即可看到新增的 `dew.response.nityPercent`,`dew.response.average`,`dew.response.max`,`dew.response.tps` 四个指标
及针对接口的对应指标

再以`spring-boot-admin`结合`spring-cloud`搭建监控中心，提供服务监控



## Spring Cloud最佳实践
最佳实践积攒了我们运用Spring Cloud过程中针对痛点，提供的我们认为最合适的解决方案。

### 开发调试

在Spring Cloud 体系下，服务调用需要依赖Eureka服务，出现了如下的问题：

- 开发期间会不断启停服务，Eureka保护机制会影响服务注册（当然这是可以关闭的）
- 多人协作时可能会出现调用到他人服务的情况（同一服务多个实例）

- 需要启动 Eureka 服务，多了一个依赖

解决这个问题，可以在使用Spring Cloud 的`RestTemplate`时，增加Ribbon的服务配置

```yaml
# <client>为service-id
<client>.ribbon.listOfServers: <直接访问的IPs>
# 如
performance-service.ribbon.listOfServers: 127.0.0.1:8812
```

>如果某个服务，只是充当依赖服务，像Config服务之类不是调试主体，还是推荐使用Eureka方式，减少本地服务启动数

### 参数校验

对接口入参进行校验的时候，那一段段`if`语句是不是很痛苦，使用`@Validated` 注解帮你轻松解决烦恼。

1. 对于基本数据类型和`String`类型，要使校验的注解生效，需在该类上方加 `@Validated` 注解
2. 对于抽象数据类型，需在形式参数前加`@Validated`注解

### Swagger离线文档

使用Swagger接口文档，支持REST API方式，但不支持文档的离线生成，对其我们进行深度的优化，支持了离线文档的生成。

- 在Test中添加一个空白的`DocTest.java`

**e.g.**

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Dew.class, AuthApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocTest {
    @Test
    public void empty() {
    }
}
```

- 在指定项目路径下，执行

```shell
mvn -Dtest=DocTest clean test -P doc
```

> 需要指定输出文档路径，api.file.name启动参数进行配置

```shell
mvn -Dtest=DocTest -Dapi.file.name=dew-example clean test -P doc
```

### Ribbon 负载均衡

Spring Cloud支持`service-dew.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule`方式选择负载均衡策略，因此你可以这样：

若指定zone，默认会优先调用相同zone的服务，此优先级高于策略配置，配置如下

```yaml
#指定属于哪个zone
eureka:
  instance:
    metadata-map:
      zone: #zone 名称

#指定region（此处region为项目在不同区域的部署，为项目规范，不同region间能互相调用）
eureka:
  client:
    region: #region名称
```

### Feign 配置特定方法超时时间

Hystrix和Ribbon的超时时间配置相互独立，以低为准，使用时请根据实际情况进行配置，配置项可以参考

```yaml
# Hystrix 超时时间配置
# 配置默认的hystrix超时时间
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=10000
# 配置特定方法的超时时间,优于默认配置
hystrix.command.<hystrixcommandkey>.execution.isolation.thread.timeoutInMilliseconds=10000
# <hystrixcommandkey>的format为FeignClassName#methodSignature,下面是示例配置
hystrix.command.PressureService#getBalance(int).execution.isolation.thread.timeoutInMilliseconds=10000

# Ribbon 超时时间配置
# 配置默认ribbon超时时间
ribbon.ReadTimeout=60000
# 配置特定服务超时时间,优于默认配置
<client>.ribbon.ReadTimeout=6000
# <client>为实际服务名,下面是示例配置
pressure-service.ribbon.ReadTimeout=5000
```

> 如果要针对某个服务做超时设置,建议使用Ribbon的配置；在同时使用Ribbon和Hystrix 时,请特别注意超时时间的配置。

### Feign接口添加HTTP请求头信息

Feign作为仿SDK的时候，其根本还是通过HTTP的方式调用接口，因而会存在Header配置场景。

在`@FeignClient`修饰类中的接口方法里添加新的形参，并加上 `@RequestHeader` 注解指定key值

**e.g.**

```java
@PostMapping(value = "ca/all", consumes = MediaType.APPLICATION_JSON_VALUE)
Resp<CustomerInfoVO> applyCA(@RequestBody CAIdentificationDTO params,
     @RequestHeader Map<String, Object> headers);
```

### Feign文件上传实践

- pom添加依赖配置

```Xml
        <dependency>
            <groupId>io.github.openfeign.form</groupId>
            <artifactId>feign-form</artifactId>
            <version>3.0.1</version>
        </dependency>
        <dependency>
            <groupId>io.github.openfeign.form</groupId>
            <artifactId>feign-form-spring</artifactId>
            <version>3.0.1</version>
        </dependency>
```

- 创建一个Configuration

```java
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultipartSupportConfig {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

}
```

- 修改Client接口代码

```java
@FeignClient(name = "demo")
public interface FeginExample {
@PostMapping(value = "images", consumes = MULTIPART_FORM_DATA_VALUE)
 Resp<String> uploadImage(
            @RequestParam MultipartFile image,
            @RequestParam("id") String id);
}
```

> `@RequestPart` 与 `@RequestParam` 效果是一样的，大家就不用花时间在这上面了

- 修改Service接口代码

```java
@RestController
public class FeginServiceExample {
  @PostMapping(value = "images", consumes = MULTIPART_FORM_DATA_VALUE)
    public Resp<String> uploadImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("id") String id,
            HttpServletRequest request) {
              return Resp.success(null);
            }
}
```

常见问题：

- `HTTP Status 400 - Required request part 'file' is not present`

> 请求文件参数的名称与实际接口接受名称不一致

- `feign.codec.EncodeException: Could not write request: no suitable HttpMessageConverter found for request type [org.springframework.mock.web.MockMultipartFile] and content type [multipart/form-data]`

> 转换器没有生效，检查一下MultipartSupportConfig

### 服务自定义降级

在微服务架构中，必然会存在服务间的相互依赖，如果某一个服务发生了宕机，为了请求的正常相应，对服务的降级以防止系统服务的大面积瘫痪，造成不可估计的金钱损失。

> 构建类继承HystrixCommand抽象类，重写run方法，getFallback方法，getFallback为run的降级，再执行excute方法即可
>
> 每个HystrixCommand的子类的实例只能excute一次

**e.g.**

```java
public class HelloHystrixCommand extends HystrixCommand<HelloHystrixCommand.Model> {

    public static final Logger logger = LoggerFactory.getLogger(HelloHystrixCommand.class);

    private Model model;

    protected HelloHystrixCommand(HystrixCommandGroupKey group) {
        super(group);
    }

    protected HelloHystrixCommand(HystrixCommandGroupKey group, HystrixThreadPoolKey threadPool) {
        super(group, threadPool);
    }

    protected HelloHystrixCommand(HystrixCommandGroupKey group, int executionIsolationThreadTimeoutInMilliseconds) {
        super(group, executionIsolationThreadTimeoutInMilliseconds);
    }

    protected HelloHystrixCommand(HystrixCommandGroupKey group, HystrixThreadPoolKey threadPool, int executionIsolationThreadTimeoutInMilliseconds) {
        super(group, threadPool, executionIsolationThreadTimeoutInMilliseconds);
    }

    protected HelloHystrixCommand(Setter setter) {
        super(setter);
    }

    public static HelloHystrixCommand getInstance(String key){
        return new HelloHystrixCommand(HystrixCommandGroupKey.Factory.asKey(key));
    }

    @Override
    protected Model run() throws Exception {
        int i = 1 / 0;
        logger.info("run:   thread id:  " + Thread.currentThread().getId());
        return model;
    }

    @Override
    protected Model getFallback() {
        return new Model("fallback");
    }

    public static void main(String[] args) throws Exception {
        HelloHystrixCommand helloHystrixCommand = HelloHystrixCommand.getInstance("dew");
        helloHystrixCommand.model = helloHystrixCommand.new Model("run");
        logger.info("main:      " + helloHystrixCommand.model + "thread id: " + Thread.currentThread().getId());
        System.out.println(helloHystrixCommand.execute());

    }


    class Model {

        public Model(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Model{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
```

### 断路保护经验分享

```yaml
# 执行的隔离策略 THREAD, SEMAPHORE 默认THREAD
hystrix.command.default.execution.isolation.strategy=THREAD
# 执行hystrix command的超时时间,超时后会进入fallback方法 默认1000
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=1000
# 执行hystrix command是否限制超时,默认是true
hystrix.command.default.execution.timeout.enabled=true
# hystrix command 执行超时后是否中断 默认true
hystrix.command.default.execution.isolation.thread.interruptOnTimeout=true
# 使用信号量隔离时,信号量大小,默认10
hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests=10
# fallback方法最大并发请求数 默认是10
hystrix.command.default.fallback.isolation.semaphore.maxConcurrentRequests=10
# 服务降级是否开启,默认为true
hystrix.command.default.fallback.enabled=true
# 是否使用断路器来跟踪健康指标和熔断请求
hystrix.command.default.circuitBreaker.enabled=true
# 熔断器的最小请求数,默认20. (这个不是很理解,欢迎补充)
hystrix.command.default.circuitBreaker.requestVolumeThreshold=20
# 断路器打开后的休眠时间,默认5000
hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds=5000
# 断路器打开的容错比,默认50
hystrix.command.default.circuitBreaker.errorThresholdPercentage=50
# 强制打开断路器,拒绝所有请求. 默认false, 优先级高于forceClosed
hystrix.command.default.circuitBreaker.forceOpen=false
# 强制关闭断路器,接收所有请求,默认false,优先级低于forceOpen
hystrix.command.default.circuitBreaker.forceClosed=false

# hystrix command 命令执行核心线程数,最大并发 默认10
hystrix.threadpool.default.coreSize=10
```

信息参见:

- https://github.com/Netflix/Hystrix/wiki/Configuration
- http://hwood.lofter.com/post/1cc7fbdc_e8c5c96

使用断路保护可有效果的防止系统雪崩，Spring Cloud 对Hystrix做了封装。

详见： http://cloud.spring.io/spring-cloud-netflix/single/spring-cloud-netflix.html#_circuit_breaker_hystrix_clients

需要说明的是Hystrix使用新线程执行代码，导致`Threadlocal`数据不能同步，为解决这个问题我们是这样做的，可供参考：

```java
public class HystrixExampleService {
    @HystrixCommand(fallbackMethod = "defaultFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public String someMethod(Map<String, Object> parameters, DewContext context) {
        // ！！！ Hystrix使用新线程执行代码，导致Threadlocal数据不能同步，
        // 使用时需要将用到的数据做为参数传入，如果需要使用Dew框架的上下文需要先传入再设值
        DewContext.setContext(context);
        try {
            Thread.sleep(new Random().nextInt(3000));
            logger.info("Normal Service Token:" + Dew.context().getToken());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "ok";
    }

    // 降级处理方法定义
    public String defaultFallback(Map<String, Object> parameters, DewContext context, Throwable e) {
        DewContext.setContext(context);
        logger.info("Error Service Token:" + Dew.context().getToken());
        return "fail";
    }
}
```

### 定时任务

使用 Spring Config 配置中心`refresh`时,在`@RefreshScope`注解的类中, `@Scheduled`注解的自动任务会失效。 建议使用实现 `SchedulingConfigurer` 接口的方式添加自动任务。

**e.g.**

```java
@Configuration
@EnableScheduling
public class SchedulingConfiguration implements SchedulingConfigurer {

    private Logger logger = LoggerFactory.getLogger(SchedulingConfiguration.class);

    @Autowired
    private ConfigExampleConfig config;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> logger.info("task1: " + config.getVersion()), triggerContext -> {
            Instant instant = Instant.now().plus(5, SECONDS);
            return Date.from(instant);
        });

        taskRegistrar.addTriggerTask(() -> logger.info("task2: " + config.getVersion()), new CronTrigger("1/3 * * * * ?"));
    }
}
```

### Spring Cloud配置扫描策略

Spring Boot 所提供的配置优先级顺序比较复杂。按照 **优先级从高到低** 的顺序，具体的列表如下所示。

1. 命令行参数。
2. 通过 `System.getProperties()` 获取的 `Java` 系统参数。
3. 操作系统环境变量。
4. 从 `java:comp/env` 得到的 `JNDI` 属性。
5. 通过 `RandomValuePropertySource` 生成的 `random.*` 属性。
6. 应用 `jar` 文件之外的属性文件。(通过 `spring.config.location` 参数)
7. 应用 `jar` 文件内部的属性文件。
8. 在应用配置 `Java` 类（包含 `@Configuration` 注解的 `Java` 类）中通过 `@PropertySource` 注解声明的属性文件。
9. 通过 `SpringApplication.setDefaultProperties` 声明的默认属性。

最佳实践：

属性文件是比较推荐的配置方式。Spring Boot在启动时会对如下目录进行搜查，读取相应配置文件。优先级从高到低。

1. 当前jar目录的 `/config` 子目录
2. 当前jar目录
3. classpath中的 `/config` 包
4. classpath

> 可以通过 `spring.config.name` 配置属性来指定不同的属性文件名称。也可以通过 spring.config.location 来添加额外的属性文件的搜索路径。如果应用中包含多个profile，可以为每个profile定义各自的属性文件，按照 `application-{profile}` 来命名。 Spring Cloud Config 配置方式就属于这种配置方式，其优先级低于本地 jar 外配置文件

使用Profile区分环境

在Spring Boot中可以使用 `application.yml` ，`application-default.yml` ， `application-dev.yml` ， `application-test.yml` 进行不同环境的配置。默认时，会读取 `application.yml` ，`application-default.yml` 这两个文件中的配置，优先级高的会覆盖优先级低的配置。无论切换到哪个环境，指定的环境的配置的优先级是最高的。

可以使用 `spring.profiles.active=dev` 指定环境。

**Tips**

- `bootstrap.yml` (jar 包外) > `bootstrap.yml` (jar包内) > `application.yml` (jar包外) > `application.yml` (jar包内)
- 当启用Spring Cloud Config配置后， Git仓库中的 `application.yml` > `[application-name].yml` >`[application-name]-[profile].yml`
- 配置的覆盖不是以文件为单位，而是以配置中的参数为单位。
- 同一参数取最先扫描到的value

### Zuul保护(隐藏)内部服务的HTTP接口

现在有一个服务接口只对内部服务提供支持，不希望被外部进行访问，幸好我们有Gateway存在，可以通过如下配置解决这个问题：

在yml配置文件里配置(`ignored-patterns`,`ignored-services`)这两项中的一项即可

**e.g.**

```yaml
zuul: #配置一项即可!
  ignored-patterns: /dew-example/**   #排除此路径
  ignored-services: dew-example       #排除此服务
```

### 缓存超时实现

Spring Cache提供了很好的注解式缓存，但默认没有超时，需要根据使用的缓存容器特殊配置

**e.g.**

```
@Bean
RedisCacheManager cacheManager() {
    final RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
    redisCacheManager.setUsePrefix(true);
    redisCacheManager.setDefaultExpiration(<过期秒数>);
    return redisCacheManager;
}
```

### 日志处理

对微服务而言 服务API调用 日志可选择 `Sleuth + Zipkin` 的方案，没有选择 Zipkin 理由如下：

1. Zipkin需要再部署一套 Zipkin 服务，多了一个依赖
2. Zipkin日志走 HTTP 协议对性能有比较大的影响，走MQ方案又会让使用方多了一个技术依赖，且Rabbit的性能也是个瓶颈，Kafka才比较适合

3. Zipkin日志存储方案中 MySQL 有明显的问题，Cassandra不错，但选型比较偏，ES最为合适

4. Zipkin方案导致 服务API调用日志与应用程序日志不统一，后则多选择 ELK 方案

我们使用`Sleuth + Slf4j + ES（可选）`解决方案，原因：

1. 简单，使用方没有额外的技术依赖，只要像普通日志一样处理即可
2. 统一，所有类型的日志都可统一使用类似Logback的日志框架记录，方便统一维护
3. 高效，可异步批量提交到ES

> 开发中我们可能会改变profile,切记同时修改`logback-spring.xml`,在`<springProfile>`标签里配上该prifile。

对日志的查询和可视化通过使用Kibana实现。

### servo内存泄漏问题

已知在某此情况下servo统计会导致内存泄漏，如无特殊需要建议关闭 `spring.metrics.servo.enabled: false`

### Spring Boot Admin 监控实践

在Spring Boot Actuator中提供很多像 `health` 、 `metrics` 等实时监控接口，可以方便我们随时跟踪服务的性能指标。Spring Boot 默认是开放这些接口提供调用的，那么就问题来了，如果这些接口公开在外网中，很容易被不法分子所利用，这肯定不是我们想要的结果。 在这里我们提供一种比较好的解决方案：

- 被监控的服务配置

```yaml
management:
  security:
    enabled: false # 关闭管理认证
  context-path: /management # 管理前缀
eureka:
  instance:
    status-page-url-path: ${management.context-path}/info
    health-check-url-path: ${management.context-path}/health
    metadata-map:
      cluster: default # 集群名称
```

- Zuul网关配置

```yaml
zuul:
  ignoredPatterns: /*/management/** # 同上文 management.context-path , 这里之所以不是 /management/** ，由于网关存在项目前缀，需要往前一级，大家可以具体场景具体配置
```

- Spring Boot Admin配置

```yaml
spring:
  application:
    name: monitor
  boot:
    admin:
      discovery:
        converter:
          management-context-path: ${management.context-path}
      routes:
        endpoints: env,metrics,dump,jolokia,info,configprops,trace,logfile,refresh,flyway,liquibase,heapdump,loggers,auditevents,hystrix.stream,turbine.stream # 要监控的内容 
      turbine:
        clusters: default  # 要监控的集群名称
        location: ${spring.application.name}

turbine:
  instanceUrlSuffix: ${management.context-path}/hystrix.stream
  aggregator:
    clusterConfig: default 
  appConfig: monitor-example,hystrix-example # 添加需要被监控的应用 Service-Id ，以逗号分隔
  clusterNameExpression: metadata['cluster']

security:
  basic:
    enabled: false

server:
  port: ...

eureka:
  instance:
    metadata-map:
      cluster: default 
    status-page-url-path: ${management.context-path}/info
    health-check-url-path: ${management.context-path}/health

  client:
    serviceUrl:
      defaultZone: ...

management:
  security:
    enabled: false
  context-path: /management # 同上文 management.context-path
```

### JDBC批量插入性能问题

如果不开启`rewriteBatchedStatements=true`，那么Jdbc会把批量插入当做一行行的单条处理，也就没有达到批量插入的效果

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/dew?useUnicode=true&characterEncoding=utf-8&rewriteBatchedStatements=true
    username: root
    password: 123456
```

对于一张七列的表，插入1500条数据，分别对Mybatis和JdbcTemplate进行测试，记录三次数据如下,可以看到，该配置对于JdbcTemplate影响是极大的，而对于Mybatis影响却不大

| RewriteBatchedStatements | Mybatis(ms) | JdbcTemplate(ms) | Dew(ms) |
| ------------------------ | ----------- | ---------------- | ------- |
| true                     | 401         | 88               | 174     |
| true                     | 427         | 78               | 167     |
| true                     | 422         | 75               | 176     |
| false                    | 428         | 1967             | 2065    |
| false                    | 410         | 2641             | 2744    |
| false                    | 369         | 2299             | 2398    |

### HTTP请求并发数性能优化

当Hystrix策略为Thread时（默认是Thread)，`hystrix.threadpool.default.maximumSize`为第一个性能瓶颈，默认值为10.

> 需要先设置`hystrix.threadpool.default.allowMaximumSizeToDivergeFromCoreSize`为true，默认为false

第二个瓶颈为springboot内置的tomcat的最大连接数，参数为`server.tomcat.maxThreads`，默认值为200

### 日志中解析message,动态显示property

在启动类的main方法中注册converter，如下`PatternLayout.defaultConverterMap.put("dew", TestConverter.class.getName());`

自定义Converter继承`DynamicConverter<ILoggingEvent>`，解析message，获取有效信息并返回解析后得到的字符串。

`e.g.`

```java
public class TestConverter extends DynamicConverter<ILoggingEvent> {

    @Override
    public String convert(ILoggingEvent event) {
        // 这里未做解析，示例代码
        return event.getMessage();
    }
}
```



## 实践案例

### 用户权限中心

实现用户登录、注册基础功能外，还进行了接口API的权限校验、Token版本号校验、单点登录、支持多种登录方式。具有其他公共业务的横向拓展能力。

### 消息中心

接入多个短信渠道商，通过策略，提高短信的发送成功率。支持微信、站内信、短信等多种方式实现。

### 电子签

接入多个电子签渠道商，统一接口和参数下，低切换成本，实现主流程的互通共用，提高服务可用性。

### 分布式定时任务

把分散的，可靠性差的计划任务纳入统一的平台，并实现集群管理调度、问题的追踪与警告、分布式部署，提高系统的可用性。

