# train
微服务实现12306

第一次使用微服务吧,挺高大上的









# 会员模块





# 日志

## 

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





