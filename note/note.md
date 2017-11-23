# 目的

通过阅读tomcat-jdbc的源码以学习一个数据库，或者可以扩展到更广意义上的连接池的实现。选择tomcat-jdbc的原因是足够的轻量，
源码足够的简洁，同时又不失核心的功能。

本工程基于JDK 9构建，在JDK 8上不需要额外的引入多余的依赖便可以使用javax.annotation包下的注解，但在JDK 9上不行，具体的解决方案
可以参考:

[Java 9: how to get access to javax.annotation.Resource at run time](https://stackoverflow.com/questions/46502001/java-9-how-to-get-access-to-javax-annotation-resource-at-run-time)

# 初始化