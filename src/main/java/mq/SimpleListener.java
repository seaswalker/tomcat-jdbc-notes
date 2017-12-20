package mq;

import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 测试用消费者.
 *
 * @author skywalker
 */
public class SimpleListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            String data = ((ActiveMQTextMessage) message).getText();
            /*if (data.equals("消息: 2")) {
                System.out.println(Thread.currentThread().getId() + "抛异常，消息: " + data);
                throw new RuntimeException();
            }*/
            System.out.println(Thread.currentThread().getId() + "收到消息: " + data);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
