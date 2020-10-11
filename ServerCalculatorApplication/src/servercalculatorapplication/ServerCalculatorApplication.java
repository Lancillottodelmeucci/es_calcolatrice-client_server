package servercalculatorapplication;

/**
 *
 * @author giova
 */
public class ServerCalculatorApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ServerCalculator server=new ServerCalculator();
        server.attendiConnessioneClient();
        server.attendiMessaggiClient();
    }
    
}
