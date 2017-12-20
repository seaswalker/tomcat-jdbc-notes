package jmx.listener;

import javax.management.Notification;
import javax.management.NotificationListener;

/**
 * {@link ServerConfigureMBean}监听器.
 *
 * @author skywalker
 */
public class ServerConfigureListener implements NotificationListener {

    @Override
    public void handleNotification(Notification notification, Object handback) {
        System.out.println("收到变更消息: " + notification.getMessage() + ", handback是个: " + handback + ".");
    }

}
