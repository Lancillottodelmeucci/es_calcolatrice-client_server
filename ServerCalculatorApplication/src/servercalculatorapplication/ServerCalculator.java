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
    public void attendiMessaggiClient(){
        try {
            for(;;){
                System.out.println("In attesa del messaggio da parte del client.");
                operazione=dati_dal_client.readLine();
                if(operazione.equals("eoc")){//end of calculus
                    break;
                }
                System.out.println("Messaggio ricevuto.");
                if(isCorretta()){
                    risposta_server='#'+operazione;
                }
                else{
                    risposta_server="Errore presente nell'operazione.";
                }
                System.out.println("Invio della risposta al client.");
                dati_al_client.writeBytes(risposta_server+'\n');
                System.out.println("Risposta inviata.");
            }
            System.out.println("Chiusura collegamento.");
            socket_client.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione.");
        }
    }
    public boolean isCorretta(){
        //ArrayList<Character> charOp=new ArrayList<>(operazione.toCharArray());
        //char[] chars=operazione.toCharArray();
        for (int i = 0; i < operazione.length(); i++) {
            //pulizia e correzione caratteri non validi e in posizioni scorrette
            switch(operazione.charAt(i)){
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    //carattere apposto
                    break;
                case '.'://la doppia virgola viene corretta dopo
                    if(i==0){
                        if(operazione.length()>1){
                            switch(operazione.charAt(i+1)){
                                case '+':
                                case '-':
                                    operazione=operazione.substring(i+1);
                                    i--;
                                    break;
                                case '*':
                                case '/':
                                    operazione=operazione.substring(i+2);
                                    i--;
                                    break;
                            }
                        }
                        else{
                            operazione=operazione+'0';
                        }
                    }
                    else if(i==operazione.length()-1){
                        switch(operazione.charAt(i-1)){
                                case '+':
                                case '-':
                                case '*':
                                case '/':
                                    operazione=operazione.substring(0,i-1);
                                    break;
                            }
                    }
                    else{
                        switch(operazione.charAt(i-1)){
                            case '+':
                            case '-':
                            case '*':
                            case '/':
                                switch(operazione.charAt(i+1)){
                                    case '+':
                                    case '-':
                                    case '*':
                                    case '/':
                                        if(i+1==operazione.length()-1){
                                            operazione=operazione.substring(0,i-1);
                                        }
                                        else{
                                            operazione=operazione.substring(0, i-1)+operazione.substring(i+1);
                                        }
                                        break;
                                }
                                break;
                        }
                    }
                    break;
                case '+':
                case '-':
                    if(i==0){
                        operazione='0'+operazione;
                    }
                    if(i==operazione.length()-1){
                        operazione=operazione.substring(0, i);
                    }
                    break;
                case '*':
                case '/':
                    if(i==0){
                        operazione=operazione.substring(1);
                        i--;
                    }
                    if(i==operazione.length()-1){
                        operazione=operazione.substring(0, i);
                    }
                    break;
                case ',':
                    operazione.replace(',','.');
                    break;
                default:
                    operazione=operazione.subSequence(0, i)+operazione.substring(i+1);
                    i--;
                    break;
            }
            if(i<operazione.length()&&i>=0){
                switch(operazione.charAt(i)){
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        switch(operazione.charAt(i-1)){
                            case '+':
                            case '-':
                            case '*':
                            case '/':
                                operazione=operazione.subSequence(0, i-1)+operazione.substring(i);
                                i--;
                                break;
                        }
                        break;
                }
            }
        }
        if(operazione.length()<1){
            return (false);
        }
        return (true);
    }
}
