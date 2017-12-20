package jmx.mxbean;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * 启动简单的MBean Server.
 *
 * @author skywalker
 */
public class Bootstrap {

    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException,
            InstanceAlreadyExistsException, MBeanException, InterruptedException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("skywalker.mxbean:type=ComplicatedPerson");
        server.registerMBean(new ComplicatedPerson(), name);

        System.out.println("waiting...");
        Thread.sleep(Long.MAX_VALUE);
    }

}
