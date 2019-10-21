import javax.jms.*;
import javax.naming.NamingException;

public class TesteProdutor {

    public static void main(String[] args) throws JMSException, NamingException {
        Fila activeMQ = new Fila();
        Destination fila = activeMQ.conectar();
        MessageProducer producer = activeMQ.getSession().createProducer(fila);
        for (int i = 0; i < 1000; i++) {
            Message message = activeMQ.getSession().createTextMessage("<pedido><id>"+i+"</id></produto>");
            producer.send(message);
        }
    }

}
