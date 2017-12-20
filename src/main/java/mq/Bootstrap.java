package mq;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动测试.
 *
 * @author zhao.xudong
 */
public class Bootstrap {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-servlet.xml");
        SimpleProducer producer = context.getBean(SimpleProducer.class);
        producer.produce();
    }

}
