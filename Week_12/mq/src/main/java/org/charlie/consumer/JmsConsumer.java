package org.charlie.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/4/10
 */
public class JmsConsumer {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Destination destination = getTopicDestination(session); // Queue模式
        // Destination destination = getQueueDestination(session); // Topic模式
        MessageConsumer consumer = session.createConsumer(destination);

        while (true) {
            TextMessage message = (TextMessage) consumer.receive();
            System.out.println(message.getText());
            session.commit();
        }

        // session.close();
        // connection.close();
    }

    private static Destination getQueueDestination(Session session) throws JMSException {
        return session.createQueue("test-queue");
    }

    private static Destination getTopicDestination(Session session) throws JMSException {
        return session.createTopic("test-topic");
    }
}
