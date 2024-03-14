```java
@Configuration
public class RedisCacheManagerConfig {
    @Bean("DB")
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 分别创建String和JSON格式序列化对象，对缓存数据key和value进行转换
        RedisSerializer<String> strSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jacksonSerial = new Jackson2JsonRedisSerializer(Object.class);
        // 解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY); // 上面注释过时代码的替代方法
        jacksonSerial.setObjectMapper(om);
        // 定制缓存数据序列化方式及时效
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)) // 设置缓存数据的时效（设置为1了小时）
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(strSerializer)) // 对当前对象的key使用strSerializer这个序列化对象，进行转换
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(jacksonSerial)) // 对value使用jacksonSerial这个序列化对象，进行转换
                .disableCachingNullValues();
        RedisCacheManager cacheManager = RedisCacheManager
                .builder(redisConnectionFactory).cacheDefaults(config).build();
        return cacheManager;
    }
}
```

```log
2024-03-14 09:42:09,818 [WebContainer : 134] INFO  LogCloudSpanReporter:47 - {"traceid":"44e31739-0ee0-472b-86a5-e51657af4e74","spanparentid":"null","spanid":"44e31739-0ee0-472b-86a5-e51657af4e74","spanname":"00000000000000000000000000000282","starttime":"1710380529804","endtime":"1710380529818","duration":14,"st_request_type":"sr","et_request_type":"ss","url":"http://localhost:8004/redis/damdService-DBTrffaic","servicetype":"SERVICE","thread":"Thread[http-nio-8004-exec-2,5,main]","servicename":"DamdService","appid":"00000000000000000000000000000282","subappid":"DamdService","appname":"damd","env":"l30055129","region":"kwe4op","interface":"http://localhost:8004/redis/damdService-DBTrffaic","methodname":"public boolean com.huawei.it.xarmor.damd.controller.KafkaProducer.isCacheContainingData(java.lang.String)","protocol":"SERVICE","userid":"null","clientip":"null","x-his-uem-traceid":"null","logtext":"Error creating bean with name 'DB' defined in com.huawei.it.xarmor.damd.infrastructure.cache.RedisCacheManagerConfig: Unsatisfied dependency expressed through method 'cacheManager' parameter 0; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.data.redis.connection.RedisConnectionFactory' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}","status":"fail"}
```
```XML
<?xml version="1.0" encoding="UTF-8"?>
<configuration status="info" monitorInterval="1800">

    <properties>
        <property name="LOG_HOME">/applog/damd/logs</property>
        <property name="every_file_size">50M</property><!-- 日志切割的最小单位 -->
    </properties>

    <appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss,SSS} [%t] %-5p %c{1}:%L - %msg%n"/>
        </Console>
        <Console name="MyBatisAppender" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss,SSS} [%t] %-5p %c{1}:%L - %msg%n"/>
        </Console>

        <RollingRandomAccessFile name="info" fileName="${LOG_HOME}/root.log"
                                 filePattern="${LOG_HOME}/root.%d{yyyy-MM-dd}-%i.log">
            <DefaultRolloverStrategy max="15">
                <Delete basePath="${LOG_HOME}/" maxDepth="2">
                    <IfFileName glob="*.log"/>
                    <IfLastModified age="7d"/>
                </Delete>
            </DefaultRolloverStrategy>
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss,SSS} [%t] %-5p %c{1}:%L - %msg%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                <SizeBasedTriggeringPolicy size="100MB"/>
            </Policies>
        </RollingRandomAccessFile>

        <RollingRandomAccessFile name="error" fileName="${LOG_HOME}/damd-error.log"
                                 filePattern="${LOG_HOME}/damd-error.%d{yyyy-MM-dd}-%i.log">
            <DefaultRolloverStrategy max="15">
                <Delete basePath="${LOG_HOME}/" maxDepth="2">
                    <IfFileName glob="*.log"/>
                    <IfLastModified age="7d"/>
                </Delete>
            </DefaultRolloverStrategy>
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss,SSS} [%t] %-5p %c{1}:%L - %msg%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                <SizeBasedTriggeringPolicy size="100MB"/>
            </Policies>
            <DefaultRolloverStrategy max="15"/>
            <Filters>
                <ThresholdFilter level="error" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
        </RollingRandomAccessFile>

        <RollingRandomAccessFile name="debug" fileName="${LOG_HOME}/damd-debug.log"
                                 filePattern="${LOG_HOME}/damd-warn.%d{yyyy-MM-dd}-%i.log">
            <DefaultRolloverStrategy max="15">
                <Delete basePath="${LOG_HOME}/" maxDepth="2">
                    <IfFileName glob="*.log"/>
                    <IfLastModified age="7d"/>
                </Delete>
            </DefaultRolloverStrategy>
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss,SSS} [%t] %-5p %c{1}:%L - %msg%n"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                <SizeBasedTriggeringPolicy size="100MB"/>
            </Policies>
            <DefaultRolloverStrategy max="15"/>
            <Filters>
                <ThresholdFilter level="debug" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
        </RollingRandomAccessFile>

        <RollingRandomAccessFile name="audit" filename="${LOG_HOME}/jalor-audit.log"  filepattern="${LOG_HOME}/%d{yyyyMMdd}-%i-jalor-audit.log">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss,SSS} [%t] %-5p %c{1}:%L - %msg%n" />
            <Policies>
                <SizeBasedTriggeringPolicy size="${sys:every_file_size}"/>
            </Policies>
            <DefaultRolloverStrategy max="100"/>
        </RollingRandomAccessFile>
    </appenders>

    <Loggers>
        <Logger name="AuditLogger" level="info" additivity="false">
            <AppenderRef ref="audit"/>
        </Logger>
        <Logger name="org.springframework" level="info"/>
        <Logger name="org.mybatis" level="info"/>
        <Logger name="com.huawei.it.xarmor.damd.old.dao" level="info"/>
        <Logger name="com.huawei.it" level="info"/>
        <Logger name="com.james.usinglog.MybatisInterceptor" level="warn" additivity="false">
            <AppenderRef ref="MyBatisAppender"/>
        </Logger>
        <Logger name="org.mybatis" level="info" additivity="false">
            <AppenderRef ref="MyBatisAppender"/>
        </Logger>

        <Root level="info" includeLocation="true">
            <AppenderRef ref="info"/>
            <AppenderRef ref="warn"/>
            <AppenderRef ref="error"/>
        </Root>
    </Loggers>
</configuration>
```

appender RollingRandomAccessFile has no parameter that matches element DefaultRolloverStrategy

The most efficient way to live reasonably is every morning to make a plan of one's day and every night to examine the results obtained.

合理生活最有效的方法是每天早上制定一天的计划，每天晚上检查结果。
