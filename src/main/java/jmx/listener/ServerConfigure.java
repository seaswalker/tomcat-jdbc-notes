package jmx.listener;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@inheritDoc}
 *
 * @author skywalker
 */
public class ServerConfigure extends NotificationBroadcasterSupport implements ServerConfigureMBean {

    private String host;
    private int port;

    private static final AtomicInteger sequence = new AtomicInteger(0);

    @Override
    public void setPort(int port) {
        this.port = port;

        Notification notification =
                new Notification("port", this, sequence.getAndIncrement(), "端口号改变为: " + port);
        super.sendNotification(notification);

        System.out.println("发送端口变更消息: " + notification.toString());
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public void setHost(String host) {
        this.host = host;

        Notification notification =
                new Notification("host", this, sequence.getAndIncrement(), "host改变为: " + host);
        super.sendNotification(notification);

        System.out.println("发送host变更消息: " + notification.toString());
    }

    @Override
    public String getHost() {
        return this.host;
    }

}
