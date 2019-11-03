package jms;

import javax.jms.*;
import javax.naming.NamingException;
import java.util.Scanner;

public class TesteConsumidorFila {

    public static void main(String[] args) throws NamingException,  JMSException {
        Fila activeMQ = new Fila();
        Destination fila = activeMQ.conectar();
        MessageConsumer consumer = activeMQ.getSession().createConsumer(fila);

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    //para confirmar o recebimento da mensagem
                    message.acknowledge();
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        new Scanner(System.in).nextLine();
        activeMQ.desconectar();
    }
}
