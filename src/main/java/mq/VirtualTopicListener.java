package mq;

import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 虚拟Topic消费者.
 *
 * @author skywalker
 */
public class VirtualTopicListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            String data = ((ActiveMQTextMessage) message).getText();
            System.out.println(Thread.currentThread().getId() + "抛异常，消息: " + data);
            throw new RuntimeException();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
