# train
微服务实现12306

第一次使用微服务吧,挺高大上的









# 会员模块





# 日志

## 添加固定代码

```java
添加固定代码
public class MemberApplication {

	private static final Logger LOG = LoggerFactory.getLogger(MemberApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MemberApplication.class);
		Environment env = app.run(args).getEnvironment();
		LOG.info("启动成功！！");
		LOG.info("测试地址: \thttp://127.0.0.1:{}/hello", env.getProperty("server.port"), env.getProperty("server.servlet.context-path"));
	}
}

```



## banner的作用



![image-20230527162600656](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230527162600656.png)





## 添加固定前缀

```
server.servlet.context-path=/member
```

![image-20230527163127642](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230527163127642.png)

单体应用的话可以不加







## logback-spring(固定代码)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 修改一下路径-->
    <property name="PATH" value="./log/member"></property>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
<!--<Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %highlight(%-5level) %blue(%-50logger{50}:%-4line) %thread %green(%-18X{LOG_ID}) %msg%n</Pattern>-->
            <Pattern>%d{mm:ss.SSS} %highlight(%-5level) %blue(%-30logger{30}:%-4line) %thread %green(%-18X{LOG_ID}) %msg%n</Pattern>
        </encoder>
    </appender>

    <appender name="TRACE_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${PATH}/trace.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${PATH}/trace.%d{yyyy-MM-dd}.%i.log</FileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <layout>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %-50logger{50}:%-4line %green(%-18X{LOG_ID}) %msg%n</pattern>
        </layout>
    </appender>

    <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${PATH}/error.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${PATH}/error.%d{yyyy-MM-dd}.%i.log</FileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <layout>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %-50logger{50}:%-4line %green(%-18X{LOG_ID}) %msg%n</pattern>
        </layout>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <root level="ERROR">
        <appender-ref ref="ERROR_FILE" />
    </root>

    <root level="TRACE">
        <appender-ref ref="TRACE_FILE" />
    </root>

    <root level="INFO">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```





.gitignore

约定大于配置

```
# 把log文件夹忽略上传git
log/
```
