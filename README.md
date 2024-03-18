https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/toc.html
https://docs.oracle.com/javase/8/docs/technotes/guides/jpda/
https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javac.html

> [MBTI 八维怎么看？](https://www.zhihu.com/question/324564842?utm_id=0)
>
> 随机游走算法 反向传播 交叉熵损失函数

> 深度神经网络DNN 循环神经网络RNN Attention机制 图神经网络GNN 卷积神经网络CNN Encoder-Decoder架构 对抗神经网络GAN

> AlexNet ResNet Transformer **Transformer架构和其他衍生模型： Bert 系列和 GPT 系列** 多模态 
《 中国人工智能大模型地图研究报告 》
Code LLM：代码生成大语言模型
《大规模预训练模型如何赋能代码智能》
[Copilot背后的功臣：OpenAI Codex](https://zhuanlan.zhihu.com/p/414210861)
GPU并行计算

> Hugging face 抱抱脸社区 https://huggingface.co/
GPT
文本生成： https://huggingface.co/gpt2?text=A+long+time+ago%2C
A Survey of Large Language Models paper
What Language Model Architecture and Pretraining Objective Work Best for Zero-Shot Generalization?
Java Deep Learning Essentials.pdf 
Graduate Texts in Mathematics 244 graph-theory.pdf
GRAPH THEORY WITH APPLICATIONS J. A. Bondy and U. S. R. Murty

> 编程规范 mutable Locale.ROOT 
浮点型数据判断相等不要直接使用==，浮点型包装类型不要用equals()或者flt.compareTo(another) == 0作相等的比较
禁止尝试与NaN进行比较运算，相等操作使用Double或Float的isNaN()方法
字符与字节的互相转换操作，要指明正确的编码方式
涉及位操作，推荐使用括号
禁止直接使用可能为null的对象，防止出现空指针引用


>《
几何原本 》 公元前 300 年左右
《
九章算术 》 公元前 150 年左右
1540
三、四次方程求解算法
1614
“对数”法则出现
1671
著名的“牛顿迭代法”
Newton
Raphson method
1936
阿兰
· 图灵（ 1912~1954

英国数学家、逻辑学家，计算机科学之父，人工智能之父

图灵机的概念的出现，被认为开启了现代数学的时代。

《 计算机器与智能 》 为后来人工智能提供开创性的构思，并提出著名的图灵测试。
“
算法 概念诞生 现代
算法 时代开启
1847
“布尔代数”
二元逻辑
1888
数学公理系统
Axiomatic system
黑盒数据驱动
白盒模型驱动
1950
s
博弈论：
纳什均衡
2000
图论：
PageRank 算法
Google
网页排名算法
1997
加密算法：
DES
1988
视频编码算法：
H.261
2009
鉴权加密算法：
Bitcoin
第一个分布式加密货币
系统
1980
统筹优化算法：
内点法求解器
2021
统筹优化算法：
华为天筹求解器
1960s
控制优化算法：
Kalman 滤波器
1970s
无线通信算法：
OFDM 算法
4G
无线编码基础
1960s
无线通信算法：
数字波束形成算法
无线
MIMO 基础
2008
无线通信算法：
Polar
算法
5G
无线编码基础
2016
通信协议算法：
BBR 拥塞控制算法
2008
通信协议算法：
Cubic 拥塞控制算法
1943
神经网络
数学模型
1950
图灵测试
1967
传统
ML
K
近邻法
KNN
1956
达特矛斯夏
季人工智能
研究计划
“
人工智能
概念诞生
1952
首次
AI 的
游戏应用
1957
Perceptron
算法
1970s
Lighthill
report 负面定调
美国
DARPA UK 大学减少预算
2022
ChatGPT
GPT
4
2017
Transformer
BERT
GPT
1
AlphaGo
ImageNet
挑战赛
2012
AlexNet
ImageNet
2015
GAN
ResNet
Attention
1998
CNN
LeNet
RNN
、 LSTM
IBM
深蓝系统
“
大模型
时代开启
2006
deep
belief
networks
“
深度学习
概念诞生
1969
单层
Perceptron
无法解决
XOR 问题
神经网络
雏形出现
AI
第一次寒冬（ 1974 1980
技术无法满足过高期望，研究投入减少
1990
多层
Perceptron
训练问题解决
AI
第二次寒冬（ 1987 1993
整体外部环境影响，如
90 石
油危机等，研究投入减少
1989
Q
learning
AI
第三次寒冬
1998 2006
CNN
实用性不如 SVM

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
