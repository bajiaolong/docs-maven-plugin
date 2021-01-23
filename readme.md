# 入门

支持JDK：1.8+

## 快速开始

### 第一步：添加依赖

maven:

```
<dependency>
  <groupId>com.github.ka1ka</groupId>
  <artifactId>JavaApiDoc</artifactId>
  <version>1.0.0</version>
</dependency>
```

### 第二步：添加插件

你可以在任意一个main入口运行下面的代码：

```xml
    <!--自动编写文档插件-->
    <plugin>
        <groupId>com.github.ka1ka</groupId>
        <artifactId>docs-maven-plugin</artifactId>
        <version>1.0.0</version>
    </plugin>
```