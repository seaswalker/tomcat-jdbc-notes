# 目的

通过阅读tomcat-jdbc的源码以学习一个数据库，或者可以扩展到更广意义上的连接池的实现。选择tomcat-jdbc的原因是足够的轻量，
源码足够的简洁，同时又不失核心的功能。

本工程基于JDK 9构建，在JDK 8上不需要额外的引入多余的依赖便可以使用javax.annotation包下的注解，但在JDK 9上不行，具体的解决方案
可以参考:

[Java 9: how to get access to javax.annotation.Resource at run time](https://stackoverflow.com/questions/46502001/java-9-how-to-get-access-to-javax-annotation-resource-at-run-time)

# 初始化

数据源的构造器如下所示:

```java
@Component
public class CustomerDataSource {
    @Init
    public void initProperties() {
       properties.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"                + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        this.dataSource = new DataSource();
        this.dataSource.setPoolProperties(properties);
    }
}
```

DataSource的构造器和setPoolProperties方法没做什么值得说的东西。其类图:

![DataSource](images/DataSource.png)

那么真正的初始化的入口在哪里呢?其实就是喜闻乐见的getConnection方法:

```java
public class DataSource {
    public Connection getConnection() throws SQLException {
        if (pool == null)
            return createPool().getConnection();
        return pool.getConnection();
    }
}
```

所以，可以看出，**DataSource的初始化本质上是连接池的初始化**。

"连接池"由org.apache.tomcat.jdbc.pool.ConnectionPool定义，这货没有任何父类，也没有实现任何接口。创建工作由DataSourceProxy的pCreatePool方法完成:

```java
private synchronized ConnectionPool pCreatePool() {
    if (pool != null) {
        return pool;
    } else {
        pool = new ConnectionPool(poolProperties);
        return pool;
    }
}
```

连接池的初始化工作由以下几步组成。

## 队列创建

```java
public class ConnectionPool {
    private BlockingQueue<PooledConnection> busy;
    private BlockingQueue<PooledConnection> idle;
  
    protected void init(PoolConfiguration properties) throws SQLException {
        busy = new LinkedBlockingQueue<>();
        if (properties.isFairQueue()) {
            idle = new FairBlockingQueue<>();
        } else {
            idle = new LinkedBlockingQueue<>();
        }
    }
}
```

由两个队列组成: 忙队列和空闲队列，嗯，和我在上家公司实现的一个对象池的思路是一样的。idle队列默认就是用的公平队列的实现，这个是做什么用的在后面会提到。

## 空闲连接清理器

init方法相关源码:

```java
public void initializePoolCleaner(PoolConfiguration properties) {
    if (properties.isPoolSweeperEnabled()) {
        poolCleaner = new PoolCleaner(this, properties.getTimeBetweenEvictionRunsMillis());
        poolCleaner.start();
    } 
}
```

isPoolSweeperEnabled方法其实是根据其它多个选项组合而来，PoolProperties.isPoolSweeperEnabled:

```java
@Override
public boolean isPoolSweeperEnabled() {
    boolean timer = getTimeBetweenEvictionRunsMillis()>0;
    boolean result = timer && (isRemoveAbandoned() && getRemoveAbandonedTimeout()>0);
    result = result || (timer && getSuspectTimeout()>0);
    result = result || (timer && isTestWhileIdle() && getValidationQuery()!=null);
    result = result || (timer && getMinEvictableIdleTimeMillis()>0);
    return result;
}
```

来总结以下这个复杂的逻辑，首先timeBetweenEvictionRunsMillis，即进行空闲连接检测的时间间隔必须大于零，默认就是5000(即5秒)。下面便是或的条件:

- 开启了遗弃连接移除并且超时时间大于0(默认60)，什么是被遗弃呢?其实就是**被应用长时间占用的连接**，被长时间占用的原因可能是应用忘记关闭了，或者是应用阻塞了，等等。注意，这里的移除会**关闭连接**，而不是对其进行回收，放到idle队列中。
- 怀疑超时时间大于零。什么是怀疑呢，其实和上面的遗弃有点类似，只不过不会移除连接，而是仅仅记录下日志和触发一条JMX事件，注意，此选项仅当没有开启遗弃移除时才会生效。
- 开启了TestWhileIdle并且validationQuery不为空，这个应该是定时检查idle连接是否依然可用。
- minEvictableIdleTimeMillis大于零，意义是连接被清除前必须在池子里待着的时间。

PoolCleaner其实是一个TimerTask:

![PoolCleaner](images/PoolCleaner.png)

PoolCleaner内部通过弱引用的方式维持了对ConnectionPool的引用，构造器源码:

```java
PoolCleaner(ConnectionPool pool, long sleepTime) {
    this.pool = new WeakReference<>(pool);
    this.sleepTime = sleepTime;
}
```

此任务通过start方法启动:

```java
public void start() {
    registerCleaner(this);
}
```

ConnectionPool.registerCleaner:

```java
private static volatile Timer poolCleanTimer = null;
private static Set<PoolCleaner> cleaners = new HashSet<>();
private static synchronized void registerCleaner(PoolCleaner cleaner) {
    unregisterCleaner(cleaner);
    cleaners.add(cleaner);
    if (poolCleanTimer == null) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(ConnectionPool.class.getClassLoader());
            poolCleanTimer = AccessController.doPrivileged(pa);
        } finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }
    poolCleanTimer.schedule(cleaner, cleaner.sleepTime,cleaner.sleepTime);
}
```

从这里可以得到几点重要的信息:

1. tomcat的连接池**全局只有一个Timer对象，所有web app持有连接池对象均是通过这一个Timer进行调度**。这一点是如何保证的呢?

   从源码中可以看出，

