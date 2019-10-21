import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Fila  {
    private Session session;
    private Connection connection;
    private InitialContext context;

    public Destination conectar() throws NamingException, JMSException {
        this.context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        this.connection = factory.createConnection();
        connection.start();


        this.session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Destination fila = (Destination) context.lookup("financeiro");
        return fila;
    }

    public void desconectar() throws JMSException, NamingException {
        this.session.close();
        this.connection.close();
        this.context.close();
    }

    public Session getSession() {
        return session;
    }

}
