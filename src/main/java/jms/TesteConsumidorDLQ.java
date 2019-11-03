package jms;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class TesteConsumidorDLQ {

    public static void main(String[] args) throws NamingException,  JMSException {
        Context context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.start();


        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //essa fila é criada para mensagens que deu erro no recebimento
        Destination fila = (Destination) context.lookup("DLQ");
        MessageConsumer consumer = session.createConsumer(fila);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                System.out.println(message);
            }
        });
        new Scanner(System.in).nextLine();

        context.close();
        connection.close();
        session.close();
    }
}
