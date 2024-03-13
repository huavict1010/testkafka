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
