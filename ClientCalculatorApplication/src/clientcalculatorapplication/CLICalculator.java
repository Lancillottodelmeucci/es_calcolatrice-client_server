/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcalculatorapplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author giova
 */
public class CLICalculator {
    String nome_server="127.0.0.1";
    int porta_server=9999;
    Socket socket;
    BufferedReader input_tastiera;
    String operazione;
    String risultato;
    DataOutputStream dati_al_server;
    BufferedReader dati_dal_server;
    public Socket connettiAlServer(){
        try {
            input_tastiera=new BufferedReader(new InputStreamReader(System.in));
            socket=new Socket(nome_server,porta_server);
            dati_al_server=new DataOutputStream(socket.getOutputStream());
            dati_dal_server=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connessione al server effettuata.");
        }
        catch(UnknownHostException e){
            System.out.println(e.getMessage());
            System.err.println("Host non riconosciuto.");
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione.");
            System.exit(1);
        }
        return(socket);
    }
    public void inviaOperazioni(){
        for(;;){
            try {
                System.out.println("Inserire l'operazione da inviare al server\n('ris' per il risultato precedente oppure 'fine' per terminare):");
                operazione=input_tastiera.readLine();
                if(operazione.toUpperCase().equals("FINE")){
                    dati_al_server.writeBytes("eoc");
                    System.out.println("Chiusura dell'esecuzione.");
                    socket.close();
                    break;
                }
                System.out.println("Invio dell'operazione al server.");
                dati_al_server.writeBytes(operazione+'\n');
                risultato=dati_dal_server.readLine();
                System.out.println("Risultato operazioni: "+risultato);
            }
            catch(IOException e){
                System.out.println(e.getMessage());
                System.out.println("Errore durante la comunicazione col server.");
                System.exit(1);
            }
        }
    }
}