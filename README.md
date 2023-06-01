# train
使用微服务实现12306











# 会员模块



# 日志

## 添加固定代码

member模块的启动类

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

```
					_ooOoo_
                   o8888888o
                   88" . "88
                   (| ^_^ |)
                   O\  =  /O
                ____/`---'\____
              .'  \\|     |//  `.
             /  \\|||  :  |||//  \
            /  _||||| -:- |||||-  \
            |   | \\\  -  /// |   |
            | \_|  ''\---/''  |   |
            \  .-\__  `-`  ___/-. /
          ___`. .'  /--.--\  `. . ___
        ."" '<  `.___\_<|>_/___.'  >'"".
      | | :  `- \`.;`\ _ /`;.`/ - ` : | |
========`-.____`-.___\_____/___.-`____.-'========
      \  \ `-.   \_ __\ /__ _/   .-` /  /
                     `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永不宕机       永无Bug
```



![image-20230527162600656](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230527162600656.png)





## 添加固定前缀

```
server.servlet.context-path=/member
在application.properties中修改
```

![image-20230527163127642](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230527163127642.png)

单体应用的话可以不加,微服务多了的话可以起到区分的作用







## logback-spring(固定代码)

作用是打印日志

放在resources目录之下

需要修改一下第4行的目录

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





关于.gitignore

具体思维是:约定大于配置

```
# 把log文件夹忽略上传git
log/
```





## HTTPClient完成测试接口

可以自己访问地址测试,但是也可以使用接口工具来测试,更加方便

![image-20230527192446400](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230527192446400.png)

![image-20230527192626740](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230527192626740.png)

结果:

![image-20230527192637058](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230527192637058.png)





也可以自己创建.http文件,然后输入gtr让他写出模板

![image-20230527192838805](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230527192838805.png)



这些文件方便别人测试,所以可以提交到git





## 增加AOP打印请求参数和返回结果

实现这个目的,一般用aop或者拦截器,这样没有侵入性

![image-20230527193404952](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230527193404952.png)

引入依赖

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.70</version>
</dependency>

<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.8.10</version>
</dependency>

```

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

![image-20230527194316957](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230527194316957.png)



复制固定代码 LogAspect

因为这个东西所有的项目都可以用到

所有我们把它放在common下的aspect文件家里就行

![image-20230528134106758](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230528134106758.png)

```java
package com.yangstar.train.common.aspect;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Aspect
@Component
public class LogAspect {
    public LogAspect() {
        System.out.println("Common LogAspect");
    }

    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 定义一个切点
     */
    //通用的写法
    @Pointcut("execution(public * com.yangstar..*Controller.*(..))")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        //增加日志流水号
        //有概率重复
        MDC.put("LOG_ID",System.currentTimeMillis() + RandomUtil.randomString(3));

        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();

        // 打印请求信息
        LOG.info("------------- 开始 -------------");
        LOG.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
        LOG.info("类名方法: {}.{}", signature.getDeclaringTypeName(), name);
        LOG.info("远程地址: {}", request.getRemoteAddr());

        // 打印请求参数
        Object[] args = joinPoint.getArgs();
        // LOG.info("请求参数: {}", JSONObject.toJSONString(args));

        // 排除特殊类型的参数，如文件类型
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }
        // 排除字段，敏感字段或太长的字段不显示：身份证、手机号、邮箱、密码等
        String[] excludeProperties = {};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        LOG.info("请求参数: {}", JSONObject.toJSONString(arguments, excludefilter));
    }

    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 排除字段，敏感字段或太长的字段不显示：身份证、手机号、邮箱、密码等
        String[] excludeProperties = {};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        LOG.info("返回结果: {}", JSONObject.toJSONString(result, excludefilter));
        LOG.info("------------- 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
        return result;
    }

}

```

成功区分两次请求

![image-20230527211347731](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230527211347731.png)





## 添加通用模块

先把demo01和member里面的内容移动到common的pom.xml里

---

在member里要引入common的依赖,相当于开发的一个依赖

![image-20230528134421602](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230528134421602.png)



---



 子模块的依赖是不用添加版本号码的,也就是:

```xml
<version> xxxxxxx</version>
```

这一行是可以省略的,父模块里有就行,以后更新修改父模块里面的东西就行



---

最后效果的话可以看见

![image-20230528134711221](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230528134711221.png)

---





关于application.properties的位置

![image-20230528121114618](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230528121114618.png)

增加了公共配置文件，需要放到resources/config/下,这个优先级更加高一些

其他模块的话放在resources下就可以 

![image-20230528121206070](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230528121206070.png)



## 网关模块

新建一个模块

![image-20230528121836790](C:\Users\yangstar\AppData\Roaming\Typora\typora-user-images\image-20230528121836790.png)

调整pom.xml,把它设置成一个网关

```xml
<dependencies>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>

</dependencies>
```

另外gateway只有一个依赖,不能引入common,也不能引入starter-web



然后复制logback-spring.xml过去





怎么添加多个微服务一起运行

![image-20230528122955518](C:\Users\yangstar\AppData\Roaming\Typora\typora-user-images\image-20230528122955518.png)

![image-20230528123028566](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230528123028566.png)





路由转发配置

![image-20230528123724796](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230528123724796.png)

yaml和properties是可以相互转换的

![image-20230528123630309](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230528123630309.png)

---

让Gateway打印日志

在vm options 参数里面填写

如果没找到

> https://blog.csdn.net/qq_42971035/article/details/124585780

![image-20230528135629599](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230528135629599.png)

```
 -Dreactor.netty.http.server.accessLogEnabled=true
```

![image-20230528135435305](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230528135435305.png)

成功打印

![image-20230528135537789](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230528135537789.png)





## 本地数据配置





## mybatis集成

引入依赖根pom和common的pom下

```
<dependency>
   <groupId>org.mybatis.spring.boot</groupId>
   <artifactId>mybatis-spring-boot-starter</artifactId>
   <version>2.1.3</version>
</dependency>

<dependency>
   <groupId>mysql</groupId>
   <artifactId>mysql-connector-java</artifactId>
   <version>8.0.32</version>
</dependency>
```



---

新增一个接口

![image-20230528152026295](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230528152026295.png)

让启动类去扫描接口

![image-20230528151929412](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230528151929412.png)











告诉sql语句在哪里

```
# mybatis xml路径
mybatis.mapper-locations=classpath:/mapper/**/*.xml
logging.level.com.jiawa.train.member.mapper=trace
```







```
# mybatis xml路径
mybatis.mapper-locations=classpath:/mapper/**/*.xml
logging.level.com.jiawa.train.member.mapper=trace
```





## 前端的搭建

npm启动

![image-20230601112242759](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230601112242759.png)



前端的jar包

![image-20230601112545945](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230601112545945.png)





## 引入AntDesignVue

### 安装

```
先安装
npm install ant-design-vue --save
```

![image-20230601113941894](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230601113941894.png)

自己引入了

### 注册

部分注册是全局注册的区别





### 图标安装

```
npm install --save @ant-design/icons-vue
```

![image-20230601120127482](https://raw.githubusercontent.com/yangstar23/picgo/main/img/image-20230601120127482.png)
