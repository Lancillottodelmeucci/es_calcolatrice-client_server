package servercalculatorapplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author giova
 */
public class ServerCalculator {
    ServerSocket socket_server=null;
    Socket socket_client=null;
    String operazione=null;
    String risposta_server=null;
    BufferedReader dati_dal_client;
    DataOutputStream dati_al_client;
    public Socket attendiConnessioneClient(){
        try {
            System.out.println("Server in esecuzione.");
            socket_server=new ServerSocket(9999);
            System.out.println("Server in attesa del client.");
            socket_client=socket_server.accept();
            System.out.println("Client connesso.");
            dati_dal_client=new BufferedReader(new InputStreamReader(socket_client.getInputStream()));
            dati_al_client=new DataOutputStream(socket_client.getOutputStream());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Errore nell'istanziamento del server.");
            System.exit(1);
        }
        return(socket_client);
    }
}
