package jmx.mbean;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * 启动简单的MBean Server.
 *
 * @author skywalker
 */
public class Bootstrap {

    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException,
            InstanceAlreadyExistsException, MBeanException, InterruptedException, InstanceNotFoundException, ReflectionException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("skywalker.mbean:type=SimplePerson");
        server.registerMBean(new SimplePerson(), name);

        //这样echo方法可以在jconsole中调用
        server.invoke(name, "echo", new Object[] {"xxx"}, new String[] {"java.lang.String"});

        System.out.println("waiting...");
        Thread.sleep(Long.MAX_VALUE);
    }

}
