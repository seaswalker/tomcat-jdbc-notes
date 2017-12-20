package jmx.listener;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * 启动监听器测试.
 *
 * @author skywalker
 */
public class Bootstrap {

    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException,
            InstanceAlreadyExistsException, MBeanException, InterruptedException, InstanceNotFoundException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("skywalker.listener:type=ServerConfigure");
        server.registerMBean(new ServerConfigure(), name);

        //这货是个本地的监听器，和jconsole中的是一个原理
        server.addNotificationListener(name, new ServerConfigureListener(), null, "大苹果");

        System.out.println("waiting...");
        Thread.sleep(Long.MAX_VALUE);
    }

}
