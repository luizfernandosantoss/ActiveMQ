package jms;

import modelo.Pedido;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.Scanner;

public class TesteConsumidorTopicoEstoqueSelector {

    public static void main(String[] args) throws JMSException, NamingException {
        System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");

        Context context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.setClientID("estoque");
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topico = (Topic) context.lookup("loja");
        MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura","ebook is null OR ebook=false",false);

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                try {
                    Pedido pedido = (Pedido) objectMessage.getObject();
                    System.out.println(pedido.getCodigo());
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
