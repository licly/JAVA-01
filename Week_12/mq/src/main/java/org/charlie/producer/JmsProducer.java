package org.charlie.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/4/10
 */
public class JmsProducer {

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = getTopicDestination(session); // Topic模式
        // Destination destination = getQueueDestination(session); // queue模式
        MessageProducer producer = session.createProducer(destination);
        producer.send(session.createTextMessage("topic : hello world"));

        session.commit();
        session.close();
        connection.close();
    }

    private static Destination getQueueDestination(Session session) throws JMSException {
        return session.createQueue("test-queue");
    }

    private static Destination getTopicDestination(Session session) throws JMSException {
        return session.createTopic("test-topic");
    }
}
