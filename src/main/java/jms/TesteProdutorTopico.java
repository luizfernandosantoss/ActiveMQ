package jms;

import modelo.Pedido;
import modelo.PedidoFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXB;
import java.io.StringWriter;

public class TesteProdutorTopico {

    public static void main(String[] args) throws JMSException, NamingException {
        Context context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topico = (Topic) context.lookup("loja");

        MessageProducer producer = session.createProducer(topico);
        //Configurando as propriedades, para seletor, para um consumidor só recebe as mensagens se atender os requesitos das propriedades
        //message.setBooleanProperty("ebook",true);
        Pedido pedido = new PedidoFactory().geraPedidoComValores();

//        StringWriter writer = new StringWriter();
//        JAXB.marshal(pedido,writer);
//        String xml = writer.toString();

        Message message = session.createObjectMessage(pedido);
        producer.send(message);


        session.close();
        connection.close();
        context.close();

    }

}
