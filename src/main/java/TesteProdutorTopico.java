import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteProdutorTopico {

    public static void main(String[] args) throws JMSException, NamingException {
        Context context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topico = (Topic) context.lookup("loja");

        MessageProducer producer = session.createProducer(topico);
        for (int i = 0; i < 2; i++) {
            Message message = session.createTextMessage("<pedido><id>"+i+"</id></produto>");
            producer.send(message);
        }

        session.close();
        connection.close();
        context.close();

    }

}
