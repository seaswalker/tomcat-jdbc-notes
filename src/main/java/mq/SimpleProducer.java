package mq;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * 简单的生产者.
 *
 * @author zhao.xudong
 */
@Component
public class SimpleProducer {

    @Resource
    private JmsTemplate jmsTemplate;
    @Resource(name = "testQueue")
    private Destination destination;
    @Resource(name = "testTopic")
    private ActiveMQTopic topic;
    @Resource(name = "virtualTopic")
    private ActiveMQTopic virtualTopic;
    int i = 0;

    public void produce() throws InterruptedException {
        Thread.sleep(3000);
        for (; i < 6; i++) {
            jmsTemplate.send(virtualTopic, session -> session.createTextMessage("消息: " + i));
        }
    }

}
