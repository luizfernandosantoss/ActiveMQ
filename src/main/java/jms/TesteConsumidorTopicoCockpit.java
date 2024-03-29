package jms;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class TesteConsumidorTopicoCockpit {

    public static void main(String[] args) throws JMSException, NamingException {
        Context context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.setClientID("cockpit");
        connection.start();
        //para confirmações de maneira automatica
        //Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //para confirmação de recebimento de mensagens de maneira manual
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Topic topico = (Topic) context.lookup("local");
        MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura");

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();

    }

}
