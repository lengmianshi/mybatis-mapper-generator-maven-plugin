mybatis-mapper代码生成器要与mybatis-mapper（项目地址：https://mapper.mybatis.io/docs/1.getting-started.html）搭配使用。

1. 在你项目的pom.xml中添加依赖：
```xml
 <plugins>
    <plugin>
        <groupId>io.github.lengmianshi</groupId>
        <artifactId>mybatis-mapper-generator-maven-plugin</artifactId>
        <version>1.0.6</version>
        <configuration>
            <configFile>${basedir}/src/main/resources/generator/generatorConfig.xml</configFile>
        </configuration>

        <dependencies>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>5.1.47</version>
            </dependency>
        </dependencies>
    </plugin>
</plugins>
```

2. 在项目的resources/generator目录下创建generatorConfig.xml，内容如下：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration >

<generatorConfiguration>
    <context>
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        url="jdbc:mysql://127.0.0.1:3306/dbname?serverTimezone=Asia/Shanghai&amp;zeroDateTimeBehavior=convertToNull&amp;useUnicode=true&amp;characterEncoding=utf8&amp;autoReconnect=true&amp;allowMultiQueries=true"
                        user="root"
                        password="123456">
        </jdbcConnection>
        <!-- pojo所在的包 -->
        <pojoGenerator package="com.demo.api.bean.entity" project="../demo-api/src/main/java"/>
        <!-- 放置mapper的包 -->
        <mapperGenerator package="com.demo.mapper" project="src/main/java"/>
        <!-- 放置service的包 -->
        <serviceGenerator package="com.demo.api.service" project="../demo-api/src/main/java"/>
        <!-- 放置serviceImpl的包 -->
        <serviceImplGenerator package="com.demo.service.impl" project="src/main/java"/>
        <!-- 放置xml文件的包 -->
        <xmlGenerator package="mappers" project="src/main/resources"/>

        <table tableName="test" className="Test" generateService="true"/>
    </context>
</generatorConfiguration>

```

说明：
- 目前仅支持mysql数据库，其他数据库未测试
- 示例中pojo和service是与当前项目不同模块的，即将pojo和service放在demo-api模块，如果你的项目不分模块，则去掉`project` 属性值里的`../demo-api/` 即可
- `table` 标签用来指定要生成的数据库表，可以同时配置多个table标签，其中，`tableName` 为数据库表名，`className` 为对应的Java Class名，如果不指定ClassName，则会自动将表名按驼峰命名规则转换，如`user_detail` 对应的类型为`UserDetail`；`generateService`表示是否生成Service，默认为true

3. 点击idea右侧中的Plugins, 双击”mybatis-mapper-genrator:generate“即可生成代码

![image.png](https://s2.loli.net/2023/04/11/VJh8dgWYOfmG14H.png)
